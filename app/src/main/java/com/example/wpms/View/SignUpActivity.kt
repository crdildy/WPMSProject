package com.example.wpms.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

//come to the sign up activity if user does not already have username and password
//once user completes all the fields in sign up they will be directed back to the
//log in screen
class SignUpActivity: AppCompatActivity() {

    private lateinit var firebaseRepository: FirebaseRepository

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var role = " "

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseRepository = FirebaseRepository()

        binding.isCaregiverButton.setOnClickListener {
            role = "caregiver"
            Toast.makeText(this, role, Toast.LENGTH_SHORT).show()
        }

        binding.isPatientButton.setOnClickListener {
            role = "patient"
            Toast.makeText(this, role, Toast.LENGTH_SHORT).show()
        }

        binding.SignUpButton.setOnClickListener{
            val name = binding.name.text.toString()
            val user = binding.username.text.toString()
            val pass = binding.password.text.toString()
            val roomNumber = "NULL"
            val confirmPass = binding.confirmPassword.text.toString()

            if(user.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            // Retrieve user's UID after successful registration
                            val userId = firebaseAuth.currentUser?.uid
                            if (userId != null) {
                                // Use the retrieved userId when adding the user to Firestore
                                firebaseRepository.addUser(userId, name, roomNumber, role)
                                val intentLogIn = Intent(this, LogInActivity::class.java)
                                startActivity(intentLogIn)
                            } else {
                                // Handle the case where user is not logged in
                                Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}