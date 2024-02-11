package com.example.wpms

// this application will act as the client
// Client will connect to the TCP server running the simulating data program
// Use socket class to establish a connection
// Once connection is establish, read the data being sent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wpms.ui.theme.WPMSTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket
import kotlinx.coroutines.*
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Main Activity", "on create call")
        setContent {
            WPMSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }


        // This allows for things to happen in the background of the app (Without this app keeps crashing on itself)
        CoroutineScope(Dispatchers.IO).launch {

            // Calls the clientTCP function to connect to server
            clientTCP()

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Network operation completed", Toast.LENGTH_LONG).show()
            }

        }

    }
}

fun clientTCP() {
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

        // Read and print the fourth line of data from the server
        val data4 = reader.readLine()
        if(data4 == "1")
        {
            println("Moisture Detected")
        } else {
            println("Moisture Not Detected")
        }


        // Close the socket
        socket.close()
    } catch (e: Exception) {
        // Handle any exceptions
        e.printStackTrace()
    }
}

// private fun updateUI(data: String) {
//        // Parse the received data (pressure and moisture readings)
//        val readings = data.split("|")
//        val pressureReading = readings[0].toDouble()
////        val moistureReading = readings[1].toDouble()
//
//        // Update UI with the received readings
//        // For example, update TextViews with pressure and moisture readings
////        pressureTextView.text = "Pressure: $pressureReading"
////        moistureTextView.text = "Moisture: $moistureReading"
//}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WPMSTheme {
        Greeting("Android")
    }
}
