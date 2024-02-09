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
import java.io.PrintWriter
import java.net.ServerSocket


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