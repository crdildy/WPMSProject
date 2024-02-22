package com.example.wpms.home_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import com.example.wpms.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class PatientHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view
        setContentView(R.layout.activity_patient_home)

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)

        // Set content for ComposeView
        composeView.setContent {
            // Remember to import CustomProgressBar composable function if it's not in the same package
            CustomProgressBar()
        }
    }
}

@Preview
@Composable
fun ProgressBarPreview() {
    CustomProgressBar()
}


@Composable
fun CustomProgressBar() {
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .padding(10.dp)
    ) {
        // Background Arc
        drawArc(
            color = Color(android.graphics.Color.parseColor("#90A4AE")),
            140f,
            260f,
            false,
            style = Stroke(10.dp.toPx(), cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )

        // Foreground Arc
        drawArc(
            color = Color(android.graphics.Color.parseColor("#ff8200")),
            140f,
            189f,
            false,
            style = Stroke(10.dp.toPx(), cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )

    }
}