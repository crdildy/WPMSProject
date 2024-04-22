package com.example.wpms.View

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import com.example.wpms.R
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wpms.databinding.ActivityPatientHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.components.XAxis


class PatientHomeActivity : AppCompatActivity() {
    private var pressureVal by mutableStateOf(1f)
    private lateinit var binding: ActivityPatientHomeBinding
    private lateinit var barChart: BarChart
    private lateinit var barDataSet: BarDataSet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set content view
        setContentView(R.layout.activity_patient_home)

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)

        // Initialize BarChart
        barChart = findViewById(R.id.barChart)
        setupBarChart()

        // Set pressure data
        setPressureData()

        // Set content for ComposeView
        composeView.setContent {
            // Remember to import CustomProgressBar composable function if it's not in the same package
            CustomProgressBar(pressureVal)
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

    private fun setPressureData() {
        // Sample pressure data for 3 hours
        val pressureData = listOf(10f, 20f, 15f, 25f, 30f, 35f) // Replace with your actual data
        val legend = barChart.legend
        legend.isEnabled

        // Prepare entries
        val entries = ArrayList<BarEntry>()
        for (i in pressureData.indices) {
            entries.add(BarEntry(i.toFloat(), pressureData[i]))
        }

        // Create data set
        barDataSet = BarDataSet(entries, "Pressure Data")

        // Set Color for Bars
        barDataSet.setColors(android.graphics.Color.BLUE, android.graphics.Color.GREEN, android.graphics.Color.YELLOW)

        // Animate Bars
        barChart.animateY(1500)

        // Create BarData object and set data
        val barData = BarData(barDataSet)
        barChart.data = barData

        // Refresh chart
        barChart.invalidate()
    }

    fun detectMoisture() {
        return
    }

    @Preview
    @Composable
    fun ProgressBarPreview() {
        CustomProgressBar(pressureVal)
    }

    @Composable
    fun CustomProgressBar(pressurePercentage: Float = 1.0f) {
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

            // Foreground Arc
            drawArc(
                brush = Brush.linearGradient(listOf(
                    Color(android.graphics.Color.parseColor("#b8002a")),
                    Color(android.graphics.Color.parseColor("#ff8200"))
                )),
                0f,
                pressurePercentage,
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

