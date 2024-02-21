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
import java.net.InetSocketAddress
import java.net.SocketTimeoutException

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

        // Works on the background off the app (If we don't have this, the app keeps crashing)
        CoroutineScope(Dispatchers.IO).launch {

            while (true) {
                try {
                    // Calls TCP server connection
                    val dataList = clientTCP()

                    // Store the returned data
                    val pressureOneVal = " ${dataList.getOrNull(1)}"
                    val pressureTwoVal = " ${dataList.getOrNull(2)}"
                    val pressureThreeVal = " ${dataList.getOrNull(3)}"
                    val moistureVal = " ${dataList.getOrNull(0)}"

                    withContext(Dispatchers.Main) {
                        // Update the UI with the received data
                        textView2.text = "Pressure level on sensor one: " + pressureOneVal
                        textView3.text = "Pressure level on sensor two:" + pressureTwoVal
                        textView4.text = "Pressure level on sensor three:" + pressureThreeVal
                        if(pressureOneVal.trim().toInt() > pressureThreshold || pressureTwoVal.trim().toInt() > pressureThreshold || pressureThreeVal.trim().toInt() > pressureThreshold) {
                            textView5.text = "Pressure Levels Exceede Threshold!"
                            //playAudio()
                            //Toast.makeText(applicationContext, "Audio started playing",Toast.LENGTH_LONG).show()
                        } else {
                            textView5.text = "Pressure Levels are Okay!"
                        }
                        if(moistureVal == "1") {
                            textView6.text = "Moissture Detected: False"
                        } else {
                            textView6.text = "Moissture Detected: True"
                        }

                        Toast.makeText(applicationContext, "Values updated", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    // Handle any exceptions, such as socket errors
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Error reading values: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }


}

fun clientTCP() : List<String> {
    // Define the server address and port
    val serverAddress = "10.0.2.2"
    val port = 12345

    // Create a socket to connect to the server
    val socket = Socket()

    // Set a timeout of 1 seconds
    socket.soTimeout = 1000

    // Connect to the server
    socket.connect(InetSocketAddress(serverAddress, port), 1000)

    // Create an ArrayList to store the received data
    val dataList = ArrayList<String>()

    // Receive data from the server
    val inputStream = socket.getInputStream()
    val reader = BufferedReader(InputStreamReader(inputStream))

    try {
        var line: String?
        while (true) {
            line = reader.readLine()
            if (line == null) {
                break
            }
            println(line)
            dataList.add(line)
        }
    } catch (e: SocketTimeoutException) {
        // Handle timeout exception
        println("Timeout: Server did not respond")
    } finally {
        // Close the socket
        socket.close()
    }

    return dataList
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
