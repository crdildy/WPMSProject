package com.example.wpms

// this application will act as the client
// Client will connect to the TCP server running the simulating data program
// Use socket class to establish a connection
// Once connection is establish, read the data being sent

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket
import kotlinx.coroutines.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.SocketTimeoutException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.json.JSONObject


var mediaPlayer : MediaPlayer? = null

class MainActivity : AppCompatActivity() {
    private lateinit var database: WPMSDatabase
    //private lateinit var pressureDataViewModel: PressureDataViewModel
    //private lateinit var pressureDB: PressureDB //declare db instance for pressure

    //private val pressureDataViewModel: PressureDataViewModel by viewModels {
    //    PressureDataViewModelFactory((application as wpmsApplication).repository)
    //}

//    private val patientViewModel: PatientViewModel by viewModels {
//        PatientViewModelFactory((application as WPMSApplication).repository)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //connects recyclerview
        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //val adapter = PressureListAdapter()
        //recyclerView.adapter = adapter
        //ecyclerView.layoutManager = LinearLayoutManager(this)

        //observes the live data in patient viewmodel
//        PatientViewModel.allPressure.observe(owner = this) { pressures ->
//            // Update the cached copy of the words in the adapter.
//            pressures.let { adapter.submitList(it) }
//        }


        //initialize Room database for pressure
         //pressureDB = Room.databaseBuilder(
          //  applicationContext,
          //  PressureDB::class.java, "pressure_db"
        //).build()


        database = Room.databaseBuilder(
            applicationContext,
            WPMSDatabase::class.java,
            "wpms_database"
        ).fallbackToDestructiveMigration()
            .build()


        //initialize the pressure data view model
        //val pressureRepository = PressureDataRepo(pressureDB.pressureDataDao())
        //pressureDataViewModel =
      //      ViewModelProvider(this, PressureDataViewModelFactory(pressureRepository))
          //      .get(PressureDataViewModel::class.java)

        //displaying incoming data from simulation
        val textView = findViewById<View>(R.id.textView1) as TextView
        val textView2 = findViewById<View>(R.id.textView2) as TextView
        val textView3 = findViewById<View>(R.id.textView3) as TextView
        val textView4 = findViewById<View>(R.id.textView4) as TextView
        val textView5 = findViewById<View>(R.id.textView5) as TextView
        val textView6 = findViewById<View>(R.id.textView6) as TextView
        // Initialization variables to store TCP random pressure and moisture values
        val pressureThreshold = 65

        // Get a reference to the CoroutineScope
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        // Works on the background off the app (If we don't have this, the app keeps crashing)
        coroutineScope.launch {
            while (isActive) {
                try {
                    // Calls TCP server connection
                    val dataList = clientTCP()

                    // Store the returned data
                    var pressureOneVal = dataList.getOrNull(1) ?: 0
                    var pressureTwoVal = dataList.getOrNull(2) ?: 0
                    var pressureThreeVal = dataList.getOrNull(3) ?: 0
                    var moistureVal = dataList.getOrNull(0) ?: 0

                    // Update the UI with the received data
                    withContext(Dispatchers.Main) {
                        textView2.text = "Pressure level on sensor one: $pressureOneVal"
                        textView3.text = "Pressure level on sensor two: $pressureTwoVal"
                        textView4.text = "Pressure level on sensor three: $pressureThreeVal"
                        if(pressureOneVal > pressureThreshold || pressureTwoVal > pressureThreshold || pressureThreeVal > pressureThreshold) {
                            textView5.text = "Pressure Levels Exceede Threshold!"
                            //playAudio()
                            //Toast.makeText(applicationContext, "Audio started playing",Toast.LENGTH_LONG).show()
                        } else {
                            textView5.text = "Pressure Levels are Okay!"
                        }
                        textView6.text = if (moistureVal == 1) {
                            "Moisture Detected: True"
                        } else {
                            "Moisture Detected: False"
                        }

                        Toast.makeText(applicationContext, "Values updated", Toast.LENGTH_SHORT)
                            .show()
                    }
                    insertPressure(pressureOneVal)
//                        , pressureTwoVal, pressureThreeVal)
                    val insertedData = checkDataFromDB()
                    Log.d("MainActivity", "Inserted data: $insertedData")
//                    insertedData.forEach { pressureData ->
//                        Log.d("MainActivity", "Pressure: ${pressureData.pressure}")
//                    }
                //insert pressure values into pressureDB
                    //val pressureData = PressureData(
                     //   pressureValue = pressureOneVal.trim().toFloat(),
                     //   timestamp = System.currentTimeMillis()
                    //)
                    //insertPressureDataIntoDB(pressureData)

                    //Printing out the data inserted
                    coroutineScope.launch {
                        database.getPressureDataDao().getAllPressureData().collect { pressureDataList ->
                            pressureDataList.forEach { pressureData ->
                                Log.d("MainActivity", "Inserted pressure: ${pressureData.pressure}")
                            }
                        }
                    }

                } catch (e: Exception) {
                    // Handle any exceptions, such as socket errors
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error reading values: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    private fun insertPressure(pressureData: Int){
        GlobalScope.launch(Dispatchers.IO) {
            //pressureDB.pressureDataDao().insert(pressureData)
            val pressureEntity = PressureData(pressure = pressureData)
            database.getPressureDataDao().insertPressureData(pressureEntity)
        }
    }

    private suspend fun checkDataFromDB(): Flow<List<PressureData>> {
        return withContext(Dispatchers.IO){
            database.getPressureDataDao().getAllPressureData()
        }
    }
//private suspend fun checkDataFromDB(): Any {
//    return database.getPressureDataDao().getAllPressureData()
//}

}

fun clientTCP(): List<Int> {
    // Define the server address and port
    val serverAddress = "10.0.2.2"
    val port = 12345

    val dataList = mutableListOf<Int>()

    // Create a socket to connect to the server
    val socket = Socket()

    try {
        // Set a timeout of 1 second
        socket.soTimeout = 1000

        // Connect to the server
        socket.connect(InetSocketAddress(serverAddress, port), 1000)

        // Send login confirmation message to the server
        val loginConfirmation = JSONObject()
        loginConfirmation.put("status", "logged_in")
        val loginConfirmationMessage = loginConfirmation.toString()
        val outputStream = socket.getOutputStream()
        outputStream.write(loginConfirmationMessage.toByteArray())
        outputStream.flush()

        // Receive data from the server
        val inputStream = socket.getInputStream()

        try {
            // Receive moisture value
            val moistureData = inputStream.readBytesFully(4)
            val moisture = ByteBuffer.wrap(moistureData).order(ByteOrder.BIG_ENDIAN).int
            println("Moisture: $moisture")
            dataList.add(moisture)

            while (true) {
                // Receive random numbers
                val randNumData = inputStream.readBytesFully(4)
                val randNumTwoData = inputStream.readBytesFully(4)
                val randNumThreeData = inputStream.readBytesFully(4)

                val randNum = ByteBuffer.wrap(randNumData).order(ByteOrder.BIG_ENDIAN).int
                val randNumTwo = ByteBuffer.wrap(randNumTwoData).order(ByteOrder.BIG_ENDIAN).int
                val randNumThree = ByteBuffer.wrap(randNumThreeData).order(ByteOrder.BIG_ENDIAN).int

                println("Random Numbers: $randNum, $randNumTwo, $randNumThree")
                dataList.add(randNum)
                dataList.add(randNumTwo)
                dataList.add(randNumThree)
            }
        } catch (e: IOException) {
            // Handle IO exceptions
            println("Error reading data: ${e.message}")
        } finally {
            // Close the input stream
            inputStream.close()
        }
    } catch (e: SocketTimeoutException) {
        // Handle socket timeout exception
        println("Connection timed out: ${e.message}")
    } catch (e: IOException) {
        // Handle other IO exceptions
        println("Error connecting to server: ${e.message}")
    } finally {
        // Close the socket
        socket.close()
    }
    return dataList
}


fun InputStream.readBytesFully(size: Int): ByteArray {
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

fun playAudio() {
    val audioURL = "https://raw.githubusercontent.com/crdildy/WPMSProject/main/AlarmSound/beep-warning-6387.mp3"

    mediaPlayer = MediaPlayer()
    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
    try {
        mediaPlayer!!.setDataSource(audioURL)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()

    } catch (e : IOException) {
        e.printStackTrace()
    }

}
