package com.example.wpms

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.wpms.MainActivity
import com.example.wpms.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLogInBinding
   private lateinit var firebaseAuth: FirebaseAuth

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
                    if (it.isSuccessful) {
                        val intentRecipe = Intent(this, MainActivity::class.java)
                        startActivity(intentRecipe)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}