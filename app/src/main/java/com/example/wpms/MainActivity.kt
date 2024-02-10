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
import androidx.lifecycle.lifecycleScope
import com.example.wpms.ui.theme.WPMSTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.*
import android.widget.Toast

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

        CoroutineScope(Dispatchers.IO).launch {

            clientTCP()

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Network operation completed", Toast.LENGTH_LONG).show()
            }

        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("Main Activity", "Coroutine")
                //val socket = Socket("10.0.2.2", 54321) // Use the server's IP address and port
                val socket = Socket("127.0.0.1",55786 )
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                while (true) {
                    val data = input.readLine()
                    Log.d("Main Activity", "Data value is: $data")
                    //update UI
                }
                //close socket
                //socket.close()
            } catch (e: Exception) {
                Log.e("Main Activity", "Error: ${e.message}")
            }
        }
    }
}

fun clientTCP() {
    // Define the server address and port
    val serverAddress = "10.0.2.2"
    val port = 12345

    // Create a socket object
    val socket = Socket(serverAddress, port)

    // Get the input stream from the socket
    val inputStream = socket.getInputStream()

    // Read data from the input stream
    val buffer = ByteArray(1024)
    val bytesRead = inputStream.read(buffer)

    // Convert the received data to a string
    val receivedData = String(buffer, 0, bytesRead)

    // Print the received data
    println(receivedData)

    // Close the socket
    socket.close()
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
