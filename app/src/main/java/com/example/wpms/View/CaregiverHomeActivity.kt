package com.example.wpms.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
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
import com.example.wpms.Adapter.UserAdapter
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R
import com.example.wpms.databinding.ActivityCaregiverHomeBinding
import com.google.firebase.auth.FirebaseAuth


class CaregiverHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaregiverHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var firebaseRepository: FirebaseRepository
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Get current user's userId
        val caregiverId = firebaseAuth.currentUser?.uid

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
        binding = ActivityCaregiverHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseRepository = FirebaseRepository()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList()) { user ->
            Toast.makeText(this, "Clicked user with id: ${user.userId}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Fetch patients and update RecyclerView
        if (caregiverId != null) {
            firebaseRepository.getCaregiverPatients(caregiverId,
                onSuccess = { patients ->
                    adapter.setData(patients)
                },
                onFailure = { exception ->
                    Toast.makeText(this, "Failed to fetch patients: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        binding.addPatient.setOnClickListener{
            Toast.makeText(this,"Patient", Toast.LENGTH_SHORT).show()
            val intentPatientAdd = Intent(this, PatientViewActivity::class.java)
            startActivity(intentPatientAdd)
        }

        // Find the ComposeView
        val composeView = findViewById<ComposeView>(R.id.composeView)


        // Set content for ComposeView
        composeView.setContent {
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