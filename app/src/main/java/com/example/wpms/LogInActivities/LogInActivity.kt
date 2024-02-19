package com.example.wpms.LogInActivities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wpms.CaregiverHomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.wpms.MainActivity
//import com.example.wpms.PatientHomeActivity
import com.example.wpms.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var isCaregiverMode = false
    //val toggleModeArea = findViewById<TextView>(R.id.ToggleModes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newUserSignup.setOnClickListener{
            val intentNewUser = Intent(this, SignUpActivity::class.java)
            startActivity(intentNewUser)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.logInButton.setOnClickListener {
            val user = binding.username.text.toString()
            val pass = binding.password.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener {
                    //Direct to correct homepage based on boolean isCaregiverMode and successful log in
                    if (it.isSuccessful) {
                        if(isCaregiverMode){
                            //Direct to caregiver homepage
                            val caregiverHome = Intent(this, CaregiverHomeActivity::class.java)
                            startActivity(caregiverHome)
                        }
                        else{
                            //Treating main activity as our patient homepage for the time being
                            val patientHome = Intent(this, MainActivity::class.java)
                            startActivity(patientHome)
                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.ToggleModes.setOnClickListener {
            isCaregiverMode = !isCaregiverMode
            if (isCaregiverMode) {
                //Handling caregiver mode
                binding.ToggleModes.text = "Caregiver Mode"
                //direct to caregiver homescreen
            } else {
                //Handles patient mode
                binding.ToggleModes.text = "Patient Mode"
                //direct to patient homescreen
            }
        }
    }
}