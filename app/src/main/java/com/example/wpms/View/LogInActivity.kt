package com.example.wpms.View

//import com.example.wpms.PatientHomeActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R
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
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

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

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LogInActivity", "User authentication successful")

                        // Retrieve user's UID after successful authentication
                        val userId = firebaseAuth.currentUser?.uid

                        userViewModel.getUserRole(
                            userId,
                            onSuccess = { role ->
                                // Now you have the user's role, you can use it for further operations
                                // For example, you can pass it to another activity using intent extras
                                if (role == "caregiver") {
                                    val intentCaregiver = Intent(this@LogInActivity, CaregiverHomeActivity::class.java)
                                    startActivity(intentCaregiver)
                                }
                                else if (role == "patient") {
                                    val intentPatient = Intent(this@LogInActivity, PatientHomeActivity::class.java)
                                    startActivity(intentPatient)
                                }
                            },
                            onFailure = { e ->
                                Toast.makeText(this, "Failed to obtain user role: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
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