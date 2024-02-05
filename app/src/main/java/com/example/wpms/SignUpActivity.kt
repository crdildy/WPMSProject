package com.example.wpms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.wpms.databinding.ActivitySignUpBinding

//come to the sign up activity if user does not already have username and password
//once user completes all the fields in sign up they will be directed back to the
//log in screen
class SignUpActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.SignUpButton.setOnClickListener{
            val user = binding.signUpUsername.text.toString()
            val pass = binding.signUpPassword.text.toString()
            val confirmPass = binding.confirmPassword.text.toString()

            if(user.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intentLogIn = Intent(this, LogInActivity::class.java)
                            startActivity(intentLogIn)
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