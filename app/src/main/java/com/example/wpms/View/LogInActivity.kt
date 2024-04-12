package com.example.wpms.View

//import com.example.wpms.PatientHomeActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R
import com.example.wpms.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseRepository: FirebaseRepository
    private var isCaregiverMode = true
    //val toggleModeArea = findViewById<TextView>(R.id.ToggleModes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener{
            val intentNewUser = Intent(this, SignUpActivity::class.java)
            startActivity(intentNewUser)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.logInButton.setOnClickListener {
            val user = binding.username.text.toString()
            val userId = firebaseAuth.currentUser?.uid
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
                            //Need to change to direct to Patient Homepage after testing
                            val patientHome = Intent(this, PatientHomeActivity::class.java)
                            startActivity(patientHome)
                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.medButton.setOnClickListener {
            isCaregiverMode = !isCaregiverMode
            Toast.makeText(this,isCaregiverMode.toString(), Toast.LENGTH_SHORT).show()
            if (isCaregiverMode) {
                //Handling caregiver mode
                val medLogoDrawable = ContextCompat.getDrawable(this, R.drawable.med_logo)
                binding.medButton.background = medLogoDrawable
                //direct to caregiver homescreen

            } else {
                //Handles patient mode
                val patientLogoDrawable = ContextCompat.getDrawable(this, R.drawable.patient_logo)
                binding.medButton.background = patientLogoDrawable
                //direct to patient homescreen
            }
        }
    }
}