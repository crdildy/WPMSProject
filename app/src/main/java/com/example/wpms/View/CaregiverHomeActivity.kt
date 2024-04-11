package com.example.wpms.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wpms.Adapter.PatientAdapter
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R
import com.example.wpms.databinding.ActivityCaregiverHomeBinding


class CaregiverHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaregiverHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientAdapter
    private lateinit var firebaseRepository: FirebaseRepository

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaregiverHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseRepository = FirebaseRepository()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PatientAdapter(emptyList())
        recyclerView.adapter = adapter

        binding.cardView.setOnClickListener{
            Toast.makeText(this,"Patient List", Toast.LENGTH_SHORT).show()
            val intentPatientList = Intent(this, PatientViewActivity::class.java)
            startActivity(intentPatientList)
        }

        binding.addPatient.setOnClickListener{
            Toast.makeText(this,"Patient", Toast.LENGTH_SHORT).show()
            val intentPatientAdd = Intent(this, NewEditPatientActivity::class.java)
            startActivity(intentPatientAdd)
        }

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)


        // Set content for ComposeView
        composeView.setContent {
            // Remember to import CustomProgressBar composable function if it's not in the same package
            ProfilePictureHolder()
        }

        firebaseRepository.getPatient(
            onSuccess = { patients ->
                adapter.setData(patients)
            },
            onFailure = { exception ->
                Toast.makeText(this, "Failed to fetch patients: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
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
                .size(250.dp)
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