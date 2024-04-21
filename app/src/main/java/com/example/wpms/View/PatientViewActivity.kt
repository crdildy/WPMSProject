package com.example.wpms.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wpms.Adapter.UserAdapter
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R

class PatientViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var firebaseRepository: FirebaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver_patients_available)

        firebaseRepository = FirebaseRepository()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList())
        recyclerView.adapter = adapter

        firebaseRepository.getPatients(
            onSuccess = { patients ->
                adapter.setData(patients)
            },
            onFailure = { exception ->
                Toast.makeText(this, "Failed to fetch patients: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}