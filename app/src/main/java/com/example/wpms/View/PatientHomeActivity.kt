package com.example.wpms.View

import android.app.AlertDialog
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wpms.DataHandler
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R
import com.example.wpms.ViewModel.PatientHomeActivityViewModel
import com.example.wpms.ViewModel.ViewModelFactory
import com.example.wpms.databinding.ActivityPatientHomeBinding
import com.example.wpms.mediaPlayer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.io.IOException
import java.sql.Timestamp


class PatientHomeActivity : AppCompatActivity() {
    //give patient activity a reference to Firebase Repo
    private lateinit var firebaseRepository: FirebaseRepository
    private lateinit var viewModel: PatientHomeActivityViewModel
    //    private lateinit var viewModel = ViewModelProvider()
    private lateinit var dataHandler: DataHandler
    private lateinit var dataObserver: Observer<List<Int>>

    private var pressureVal by mutableStateOf(1f)
    private lateinit var binding: ActivityPatientHomeBinding
    private lateinit var barChart: BarChart
    private lateinit var barDataSet: BarDataSet
    private val pressureData = mutableListOf<Float>()
    private val pressureThreshold by mutableStateOf(85)
    private var alertDialog: AlertDialog? = null

    private var isMoist by mutableStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_patient_home)

        firebaseRepository = FirebaseRepository()
        var viewModel = ViewModelProvider(this, ViewModelFactory(FirebaseRepository()))
        dataHandler = DataHandler(firebaseRepository)
        observeData()
        dataHandler.startDataRetrieval()

//        binding.settingsButton.setOnClickListener{
//            val intentSettings = Intent(this, PatientSettingsActivity::class.java)
//            startActivity(intentSettings)
//        }

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)
        // Initialize BarChart
        barChart = findViewById(R.id.barChart)
        setupBarChart()
        // Set pressure data
//        setPressureData()
        // Set content for ComposeView
        composeView.setContent {
            // Remember to import CustomProgressBar composable function if it's not in the same package
            CustomProgressBar(pressureVal, 100.0F)
        }
    }

    private fun observeData() {
        dataObserver = Observer { dataList ->
            // Insert pressure data into Firestore
            if (dataList.size >= 4) {
                val deviceID = "your_device" // You need to define how you obtain the device ID
                val moisture = dataList[0]
                Log.d("PatientHomeActivity", "moisture: $moisture")
                val pressure_center = dataList[1]
                Log.d("PatientHomeActivity", "Pressure Center: $pressure_center")
                val pressure_left = dataList[2]
                Log.d("PatientHomeActivity", "Pressure left: $pressure_left")
                val pressure_right = dataList[3]
                Log.d("PatientHomeActivity", "Pressure right: $pressure_right")
                val timestamp = Timestamp(System.currentTimeMillis())
                Log.d("PatientHomeActivity", "Timestamp: $timestamp")

                isMoist = moisture

//                pressureData.add(pressure_center.toFloat())
//                pressureData.add(pressure_left.toFloat())
//                pressureData.add(pressure_right.toFloat())

//                calculate the average for the progress bar
                val avgPressureProgressBar = (pressure_center + pressure_left + pressure_right) / 3
                updateProgressBar(avgPressureProgressBar)
                //Calculate the average pressure for each sensor
//                val averagePressureCenter = pressureData.average()
//                val averagePressureLeft = pressureData.average()
//                val averagePressureRight = pressureData.average()

                // Insert pressure & moisture data into Firestore
                firebaseRepository.insertPressureData(deviceID, pressure_center, pressure_left, pressure_right, timestamp)
                firebaseRepository.insertMoistureData(deviceID, moisture, timestamp)

                //Breach detection
                var isPressureDetected = pressure_center > pressureThreshold || pressure_left > pressureThreshold || pressure_right > pressureThreshold
                var isMoistDetected = isMoist == 1
                firebaseRepository.insertBreach(deviceID, isMoistDetected, isPressureDetected, timestamp)

                // Update bar chart data set
                val entries = ArrayList<BarEntry>()
                entries.add(BarEntry(0f, pressure_center.toFloat()))
                entries.add(BarEntry(1f, pressure_left.toFloat()))
                entries.add(BarEntry(2f, pressure_right.toFloat()))
                val dataSet = BarDataSet(entries, "Pressure Data")
                dataSet.setColors(android.graphics.Color.BLUE, android.graphics.Color.GREEN, android.graphics.Color.YELLOW)
                val data = BarData(dataSet)
                barChart.data = data
                barChart.invalidate()
//                entries.add(BarEntry(0f, averagePressureCenter.toFloat()))
//                entries.add(BarEntry(1f, averagePressureLeft.toFloat()))
//                entries.add(BarEntry(2f, averagePressureRight.toFloat()))
//                for (entry in entries){
//                    barDataSet.addEntry(entry)
//                }
//                barDataSet.addEntry(BarEntry(barDataSet.entryCount.toFloat(), pressure_center.toFloat()))
//                barDataSet.clear()
//                barDataSet.label = "Pressure Data"
//
//                // Refresh chart
//                barChart.data.notifyDataChanged()
//                barChart.notifyDataSetChanged()
//                barChart.invalidate()

                // Check for alerts
                if (isPressureDetected || isMoistDetected) {
                    var alertMessage = ""
                    if (isPressureDetected) alertMessage += "High pressure detected!\n"
                    if (isMoistDetected) alertMessage += "Moisture detected!"

                    showAlertWithSound(alertMessage)
                }
            } else {
                Log.e("PatientHomeActivity", "Insufficient data received")
            }
        }

        // Observe data changes
        dataHandler.observeData().observe(this, dataObserver)
    }

    private fun updateProgressBar(avgPressureProgressBar: Int) {
        runOnUiThread{
            binding.composeView.setContent {
                CustomProgressBar(avgPressureProgressBar.toFloat(), 100.0F)
            }
        }
    }

    fun showAlertWithSound(message: String) {
        // Check if the current alert dialog is showing and return if true to prevent multiple dialogs
        if (alertDialog?.isShowing == true) return

        playAlertSound()
        alertDialog = AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .create().also {
                it.show()
            }
    }

    fun playAlertSound() {
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

    private fun setupBarChart() {
        // Configure bar chart
        barChart.description.isEnabled = false
        barChart.setPinchZoom(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawGridBackground(false)

        // Customize X-axis
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        // Customize Y-axis
        val leftAxis = barChart.axisLeft
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(false)

        // Disable right axis
        barChart.axisRight.isEnabled = false
    }

    //Setting the pressure values on the charts
//    private fun setPressureData() {
//        // Sample pressure data for 3 hours
////        val pressureData = listOf(10f, 20f, 15f, 25f, 30f, 35f) // Replace with your actual data
////        val pressureData = mutableListOf<Int>()
////        val deviceID = "Device ID 2"
////        val timestamp = Timestamp(System.currentTimeMillis())
//        val entries = ArrayList<BarEntry>()
//        entries.add(BarEntry(0f, 0f)) // Center
//        entries.add(BarEntry(1f, 0f)) // Left
//        entries.add(BarEntry(2f, 0f)) // Right
//        val legend = barChart.legend
//        legend.isEnabled
//
//        // Create data set
//        barDataSet = BarDataSet(entries, "Pressure Data")
//        // Set Color for Bars
//        barDataSet.setColors(android.graphics.Color.BLUE, android.graphics.Color.GREEN, android.graphics.Color.YELLOW)
//        // Animate Bars
//        barChart.animateY(1500)
//
//        // Create BarData object and set data
//        val barData = BarData(barDataSet)
//        barChart.data = barData
//        // Refresh chart
//        barChart.invalidate()
//    }

    fun detectMoisture() {
        return
    }

//    @Preview
//    @Composable
//    fun ProgressBarPreview() {
//        CustomProgressBar(pressureVal)
//    }

    @Composable
    fun CustomProgressBar(pressurePercentage: Float = 0f, maxPressure: Float) {
        Canvas(
            modifier = Modifier
                .size(150.dp)
                .padding(20.dp)
                .rotate(140f)
        ) {
            val radius = size.minDimension / 2
            // Background Arc
            drawArc(
                color = Color(android.graphics.Color.parseColor("#90A4AE")),
                0f,
                260f,
                false,
                style = Stroke(25.dp.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )

            val angle = pressurePercentage * 260f / maxPressure

            // Foreground Arc
            drawArc(
                brush = Brush.linearGradient(listOf(
                    Color(android.graphics.Color.parseColor("#b8002a")),
                    Color(android.graphics.Color.parseColor("#ff8200"))
                )),
                0f,
                angle,
                false,
                style = Stroke(25.dp.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )

            // Profile Picture
            drawCircle(
                color = Color.White,
                radius =  radius - 25.dp.toPx(),
            )
            drawCircle(
                color = Color.Gray,
                radius = radius - 30.dp.toPx(),
            )
        }
    }
}
