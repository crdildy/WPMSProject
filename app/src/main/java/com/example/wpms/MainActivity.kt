package com.example.wpms

// this application will act as the client
// Client will connect to the TCP server running the simulating data program
// Use socket class to establish a connection
// Once connection is establish, read the data being sent

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket
import kotlinx.coroutines.*
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import java.io.IOException

var mediaPlayer : MediaPlayer? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<View>(R.id.textView1) as TextView
        val textView2 = findViewById<View>(R.id.textView2) as TextView
        val textView3 = findViewById<View>(R.id.textView3) as TextView
        val textView4 = findViewById<View>(R.id.textView4) as TextView
        val textView5 = findViewById<View>(R.id.textView5) as TextView
        val textView6 = findViewById<View>(R.id.textView6) as TextView

        // Initialization variables to store TCP random pressure and moisture values
        var pressureOneVal = ""
        var pressureTwoVal = ""
        var pressureThreeVal = ""
        var moistureVal = ""
        val pressureThreshold = 65

        // Works on the background off the app (If we dont have this app keeps crashing)
        CoroutineScope(Dispatchers.IO).launch {

            // Calls TCP server connection
            val dataList = clientTCP()

            // Store the returned data
            pressureOneVal =  " ${dataList.getOrNull(0)}"
            pressureTwoVal =  " ${dataList.getOrNull(1)}"
            pressureThreeVal =  " ${dataList.getOrNull(2)}"
            moistureVal =  " ${dataList.getOrNull(3)}"

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Network operation completed", Toast.LENGTH_LONG).show()
            }

        }

        //delay method
        val runnable = Runnable {
            textView2.text = "Pressure level on sensor one: " + pressureOneVal
            textView3.text = "Pressure level on sensor two:" + pressureTwoVal
            textView4.text = "Pressure level on sensor three:" + pressureThreeVal
            if(pressureOneVal.trim().toInt() > pressureThreshold || pressureTwoVal.trim().toInt() > pressureThreshold || pressureThreeVal.trim().toInt() > pressureThreshold) {
                textView5.text = "Pressure Levels Exceede Threshold!"
                playAudio()
                Toast.makeText(applicationContext, "Audio started playing",Toast.LENGTH_LONG).show()
            } else {
                textView5.text = "Pressure Levels are Okay!"
            }
            if(moistureVal == "1") {
                textView6.text = "Moissture Detected: False"
            } else {
                textView6.text = "Moissture Detected: True"
            }
            textView.text = "Reading Done."
        }

        //setting up parameters for the delay method
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, 15000)
        textView.text = "Reading Please Wait..."

    }


}

fun clientTCP(): List<String> {
    // Define the server address and port
    val serverAddress = "10.0.2.2"
    val port = 12345

    try {
        // Create a socket to connect to the server
        val socket = Socket(serverAddress, port)

        // Create input stream to read data from the socket
        val inputStream = socket.getInputStream()
        println(inputStream)
        val reader = BufferedReader(InputStreamReader(inputStream))
        println(reader)

        // Read data from the server
        val data1 = reader.readLine()
        println("Received data from server: $data1")

        // Read and print the second line of data from the server
        val data2 = reader.readLine()
        println("Received data from server: $data2")

        // Read and print the third line of data from the server
        val data3 = reader.readLine()
        println("Received data from server: $data3")

        // Read and print the third line of data from the server
        val data4 = reader.readLine()
        println("Received data from server: $data4")

        // Close the socket
        socket.close()

        return listOf(data1, data2, data3, data4)
    } catch (e: Exception) {
        // Handle any exceptions
        e.printStackTrace()
        return emptyList()
    }
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
