package com.example.wpms.View

//import com.example.wpms.PatientHomeActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.ViewModel.UserViewModel
import com.example.wpms.ViewModel.UserViewModelFactory
import com.example.wpms.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseRepository: FirebaseRepository
    //val toggleModeArea = findViewById<TextView>(R.id.ToggleModes)

    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseRepository = FirebaseRepository()

        val viewModelFactory = UserViewModelFactory(firebaseRepository)
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        binding.sign.setOnClickListener{
            val intentNewUser = Intent(this, SignUpActivity::class.java)
            startActivity(intentNewUser)
        }

        binding.log.setOnClickListener {
            val user = binding.username.text.toString()
            val pass = binding.password.text.toString()
            var userRole: String? = null

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LogInActivity", "User authentication successful")

                        // Retrieve user's UID after successful authentication
                        val userId = firebaseAuth.currentUser?.uid

                        userViewModel.getUserRole(
                            userId,
                            onSuccess = { role ->
                                userRole = role
                            },
                            onFailure = {
                                Toast.makeText(this, "Failed to obtain user role for: $userRole", Toast.LENGTH_SHORT).show()

                            }
                        )
                        userRole ?: ""

                        // Now you have the user's UID, you can use it for further operations
                        // For example, you can pass it to another activity using intent extras
                        if (userRole == "caregiver") {
                            val intentCaregiver = Intent(this@LogInActivity, CaregiverHomeActivity::class.java)
                            startActivity(intentCaregiver)
                        }
                        else if (userRole == "patient") {
                            val intentPatient = Intent(this@LogInActivity, PatientHomeActivity::class.java)
                            startActivity(intentPatient)
                        }
                    } else {
                        Log.e("LogInActivity", "User authentication failed")
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill out the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}