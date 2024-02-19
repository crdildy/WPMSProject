package com.example.wpms

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CaregiverHomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver_home)
        val welcomeTV = findViewById<TextView>(R.id.WelcomeTV)
    }
}