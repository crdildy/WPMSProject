package com.example.wpms.add_activities

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wpms.R
import com.example.wpms.repository.PatientRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewEditPatientActivity : AppCompatActivity() {

    private lateinit var patientRepository: PatientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_patient)
    }
}