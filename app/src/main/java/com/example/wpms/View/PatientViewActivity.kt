package com.example.wpms.View

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wpms.Adapter.UserAdapter
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.Model.Patient
import com.example.wpms.R
import com.google.firebase.auth.FirebaseAuth

class PatientViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var firebaseRepository: FirebaseRepository
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver_patients_available)

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

        firebaseRepository = FirebaseRepository()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList()) { user ->
            if (caregiverId != null) {
                firebaseRepository.addPatientToList(caregiverId, user.userId)
            }
            Toast.makeText(this, "Added ${user.name} to your list of patients!", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        firebaseRepository.getPatients(
            onSuccess = { users ->
                val patients = users.map { user ->
                    Patient(
                        userId = user.userId,
                        name = user.name,
                        room = "", // You'll need to fetch the room number for each user
                        caregivers = listOf() // You'll need to fetch the caregivers for each user
                    )
                }
                adapter.setData(patients)
            },
            onFailure = { exception ->
                Toast.makeText(this, "Failed to fetch patients: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}