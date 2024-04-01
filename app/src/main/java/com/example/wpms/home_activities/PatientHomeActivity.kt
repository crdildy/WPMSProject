package com.example.wpms.home_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.compose.ui.platform.ComposeView
import com.example.wpms.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.wpms.databinding.ActivityLogInBinding
import com.example.wpms.databinding.ActivityPatientHomeBinding
import kotlin.math.cos
import kotlin.math.log
import kotlin.math.sin
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class PatientHomeActivity : AppCompatActivity() {
    private var pressureVal by mutableStateOf(1f)
    private lateinit var binding: ActivityPatientHomeBinding
    lateinit var barChart: BarChart
    lateinit var barDataSet: BarDataSet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set content view
        setContentView(R.layout.activity_patient_home)

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)


        // Set content for ComposeView
        composeView.setContent {
            // Remember to import CustomProgressBar composable function if it's not in the same package
            CustomProgressBar(pressureVal)
        }
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

