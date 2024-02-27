package com.example.wpms.home_activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wpms.R

class CaregiverHomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver_home)
        val welcomeTV = findViewById<TextView>(R.id.WelcomeTV)
    }
}