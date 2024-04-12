package com.example.wpms

// this application will act as the client
// Client will connect to the TCP server running the simulating data program
// Use socket class to establish a connection
// Once connection is establish, read the data being sent

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.InvalidationTracker
import androidx.room.Room
import com.example.wpms.Entities.PressureData
import com.example.wpms.Model.PressureDB
import com.example.wpms.Model.PressureDataViewModel
import com.example.wpms.Model.PressureDataViewModelFactory
import com.example.wpms.repository.PressureDataRepo
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import androidx.lifecycle.Observer

var mediaPlayer : MediaPlayer? = null

class MainActivity : AppCompatActivity() {
//    private lateinit var pressureDataViewModel: PressureDataViewModel
//    private lateinit var pressureDB: PressureDB //declare Room db instance for pressure
    lateinit var dataHandler: DataHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Initialize Firebase Firestore database
        FirebaseApp.initializeApp(this)
        if (FirebaseApp.getApps(this).isNotEmpty()) {
            Log.d("ApplicationClass", "Firebase initialized successfully")
        } else {
            Log.e("ApplicationClass", "Firebase initialization failed")
        }
        //initialize Room database for pressure, Uncomment later if want to utilize Room for pressure locally
//         pressureDB = Room.databaseBuilder(
//            applicationContext,
//            PressureDB::class.java,
//            "pressure_db"
//        ).fallbackToDestructiveMigration()
//          .build()

        //initialize the pressure data view model, do we need this here?
//        val pressureRepository = PressureDataRepo(pressureDB.pressureDataDao())
//        pressureDataViewModel =
//            ViewModelProvider(this, PressureDataViewModelFactory(pressureRepository))
//                .get(PressureDataViewModel::class.java)

        //Initialize the DataHandler & start data retrieval
        dataHandler = DataHandler.getInstance()
        dataHandler.startDataRetrieval()
        //displaying incoming data from simulation on activity_main.xml
        val textView = findViewById<View>(R.id.textView1) as TextView
        val textView2 = findViewById<View>(R.id.textView2) as TextView
        val textView3 = findViewById<View>(R.id.textView3) as TextView
        val textView4 = findViewById<View>(R.id.textView4) as TextView
        val textView5 = findViewById<View>(R.id.textView5) as TextView
        val textView6 = findViewById<View>(R.id.textView6) as TextView
        val pressureThreshold = 65
        dataHandler.observeData().observe(this, Observer { dataList ->
            Log.d("MainActivity", "Updating UI with data: $dataList")
            // Update your UI here
            if (dataList.isNotEmpty()) {
                val moistureVal = dataList[0]
                Log.d("MainActivity", "Moisture: $moistureVal")
                val pressureOneVal = dataList.getOrNull(1) ?: 0
                Log.d("MainActivity", "P1: $pressureOneVal")
                val pressureTwoVal = dataList.getOrNull(2) ?: 0
                Log.d("MainActivity", "P2: $pressureTwoVal")
                val pressureThreeVal = dataList.getOrNull(3) ?: 0
                Log.d("MainActivity", "P3: $pressureThreeVal")
                // Update TextViews with the received data
                textView2.text = "Pressure level on sensor one: $pressureOneVal"
                textView3.text = "Pressure level on sensor two: $pressureTwoVal"
                textView4.text = "Pressure level on sensor three: $pressureThreeVal"
                textView5.text =
                    if (pressureOneVal > pressureThreshold || pressureTwoVal > pressureThreshold || pressureThreeVal > pressureThreshold) {
                        "Pressure Levels Exceed Threshold!"
                        // You can also call playAudio() here if you want to play the alarm sound
                    } else {
                        "Pressure Levels are Okay!"
                    }
                textView6.text = if (moistureVal == 1) {
                    "Moisture Detected: True"
                } else {
                    "Moisture Detected: False"
                }
            }
        })
    }

fun clientTCP(): List<String> {
    // Define the server address and port
    val serverAddress = "10.0.2.2"
    val port = 12345

    val dataList = mutableListOf<String>()

    // Create a socket to connect to the server
    val socket = Socket()

    try {
        // Set a timeout of 1 second
        socket.soTimeout = 1000

        // Connect to the server
        socket.connect(InetSocketAddress(serverAddress, port), 1000)

        // Receive data from the server
        val inputStream = socket.getInputStream()

        try {
            // Receive moisture value
            val moistureData = inputStream.readBytesFully(4)
            val moisture = ByteBuffer.wrap(moistureData).order(ByteOrder.BIG_ENDIAN).int
            println("Moisture: $moisture")
            dataList.add("Moisture: $moisture")

            while (true) {
                // Receive random numbers
                val randNumData = inputStream.readBytesFully(4)
                val randNumTwoData = inputStream.readBytesFully(4)
                val randNumThreeData = inputStream.readBytesFully(4)

                val randNum = ByteBuffer.wrap(randNumData).order(ByteOrder.BIG_ENDIAN).int
                val randNumTwo = ByteBuffer.wrap(randNumTwoData).order(ByteOrder.BIG_ENDIAN).int
                val randNumThree = ByteBuffer.wrap(randNumThreeData).order(ByteOrder.BIG_ENDIAN).int

                println("Random Numbers: $randNum, $randNumTwo, $randNumThree")
                dataList.add("$randNum")
                dataList.add("$randNumTwo")
                dataList.add("$randNumThree")
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

}
