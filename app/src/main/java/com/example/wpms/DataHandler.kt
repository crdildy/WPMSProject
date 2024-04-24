package com.example.wpms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wpms.Model.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.system.measureTimeMillis

class DataHandler(repository: FirebaseRepository) {
    private val dataLiveData: MutableLiveData<List<Int>> = MutableLiveData()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: DataHandler? = null

//        fun getInstance(): DataHandler {
//            return instance ?: synchronized(this) {
//                instance ?: DataHandler().also { instance = it }
//            }
//        }
    }

    fun startDataRetrieval() {
        Log.d("DataHandler", "call to start data retrieval")
        coroutineScope.launch {
            try {
                val dataList = clientTCP()
                Log.d("DataHandler", "Received data: $dataList") // Log statement to check data reception
                withContext(Dispatchers.Main) {
                    dataLiveData.value = dataList
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("DataHandler", "Error reading values: ${e.message}")
                }
            }
        }
    }

    fun observeData(): LiveData<List<Int>> {
        return dataLiveData
    }

    private fun clientTCP(): List<Int> {
        // Define the server address and port
        val serverAddress = "10.0.2.2"
        val port = 12345

        // Create a socket to connect to the server
        val socket = Socket()

        try {
            // Set a timeout of 5 seconds
            socket.soTimeout = 5000

            // Connect to the server
            socket.connect(InetSocketAddress(serverAddress, port), 1000)

            // Send login confirmation message to the server
            val loginConfirmation = JSONObject()
            loginConfirmation.put("status", "logged_in")
            val loginConfirmationMessage = loginConfirmation.toString()
            val outputStream = socket.getOutputStream()
            outputStream.write(loginConfirmationMessage.toByteArray())
            outputStream.flush()

            // Timer for sending switch command every 6 seconds
            var lastSwitchTime = System.currentTimeMillis()

            // Receive data from the server
            val inputStream = socket.getInputStream()

            try {
                while (true) {
                    val dataList = mutableListOf<Int>() // Create a new list for each set of data

                    // Check if it's time to send a switch command
                    if (System.currentTimeMillis() - lastSwitchTime > 6000) {
                        outputStream.write("switch".toByteArray())
                        outputStream.flush()
                        lastSwitchTime = System.currentTimeMillis()
                        Log.d("SwitchCommand", "Switch command sent")
                    }

                    // Receive moisture value
                    val moistureData = inputStream.readBytesFully(4)
                    val moisture = ByteBuffer.wrap(moistureData).order(ByteOrder.BIG_ENDIAN).int
                    dataList.add(moisture)

                    // Receive random numbers (if needed)
                    val randNumData = inputStream.readBytesFully(4)
                    val randNumTwoData = inputStream.readBytesFully(4)
                    val randNumThreeData = inputStream.readBytesFully(4)

                    val randNum = ByteBuffer.wrap(randNumData).order(ByteOrder.BIG_ENDIAN).int
                    val randNumTwo = ByteBuffer.wrap(randNumTwoData).order(ByteOrder.BIG_ENDIAN).int
                    val randNumThree = ByteBuffer.wrap(randNumThreeData).order(ByteOrder.BIG_ENDIAN).int

                    dataList.add(randNum)
                    dataList.add(randNumTwo)
                    dataList.add(randNumThree)

                    // Log the contents of the dataList
                    Log.d("DataHandler", "Updated dataList: $dataList")

                    // After receiving data, update the LiveData with the new list
                    dataLiveData.postValue(dataList)
                }
            } catch (e: IOException) {
                // Handle IO exceptions
                Log.e("DataHandler", "Error reading data: ${e.message}")
            } finally {
                // Close the input stream
                inputStream.close()
            }
        } catch (e: SocketTimeoutException) {
            // Handle socket timeout exception
            Log.e("DataHandler", "Connection timed out: ${e.message}")
        } catch (e: IOException) {
            // Handle other IO exceptions
            Log.e("DataHandler", "Error connecting to server: ${e.message}")
        } finally {
            // Close the socket
            socket.close()
        }

        return emptyList() // Return an empty list if the loop exits
    }

    private fun InputStream.readBytesFully(size: Int): ByteArray {
        // Create a buffer to store the read bytes
        val buffer = ByteArray(size)
        // Track the total number of bytes read
        var read = 0
        // Continue reading until we have read `size` bytes
        while (read < size) {
            // Read bytes into the buffer, starting at the current position
            val bytesRead = this.read(buffer, read, size - read)
            // Check if we reached the end of the stream
            if (bytesRead == -1) {
                // If so, throw an exception
                throw IllegalStateException("Stream ended before reading $size bytes")
            }
            // Update the total number of bytes read
            read += bytesRead
        }
        // Return the read bytes
        return buffer
    }
}
