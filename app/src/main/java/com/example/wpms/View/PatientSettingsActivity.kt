package com.example.wpms.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.wpms.R

class PatientSettingsActivity : AppCompatActivity() {
    private lateinit var profilePicture: ImageView
    private lateinit var selectProfilePictureButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_settings)

        profilePicture = findViewById(R.id.profilePicture)
        selectProfilePictureButton = findViewById(R.id.selectProfilePictureButton)

        selectProfilePictureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)

            profilePicture.setImageBitmap(bitmap)
        }
    }
}