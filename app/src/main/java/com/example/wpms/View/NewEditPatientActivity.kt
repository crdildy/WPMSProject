package com.example.wpms.View

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.example.wpms.Model.FirebaseRepository
import com.example.wpms.R

class NewEditPatientActivity : AppCompatActivity() {

    private lateinit var firebaseRepository: FirebaseRepository

    private lateinit var nameEditText: EditText
    private lateinit var roomNumberPicker: NumberPicker
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_patient)

        firebaseRepository = FirebaseRepository()

        nameEditText = findViewById(R.id.nameEditText)
        roomNumberPicker = findViewById(R.id.roomNumberPicker)
        addButton = findViewById(R.id.addButton)

        roomNumberPicker.minValue = 1
        roomNumberPicker.maxValue = 100

        addButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val roomNumber = roomNumberPicker.value.toString()

            if (name.isNotEmpty()) {
                val userId = firebaseRepository.getCurrentUserId()
                if (userId != null) {
                    firebaseRepository.addPatient(userId, name, roomNumber)
                    finish()
                } else {
                    // Handle the case where user is not logged in
                }
            } else {
                // Handle the case where name is empty
            }
        }
    }
}
