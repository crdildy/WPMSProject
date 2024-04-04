package com.example.wpms.home_activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import com.example.wpms.R
import com.example.wpms.databinding.ActivityCaregiverHomeBinding


class CaregiverHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaregiverHomeBinding
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaregiverHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)


        // Set content for ComposeView
        composeView.setContent {
            // Remember to import CustomProgressBar composable function if it's not in the same package
            ProfilePictureHolder()
        }
    }

    @Preview
    @Composable
    fun ProfilePicturePreview() {
        ProfilePictureHolder()
    }

    @Composable
    fun ProfilePictureHolder() {
        Canvas(
            modifier = Modifier
                .size(150.dp)
                .padding(20.dp)
                .rotate(140f)
        ) {
            val radius = size.minDimension / 2

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