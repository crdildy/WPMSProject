package com.example.wpms.Model

data class Patient(
    val userId: String, // Unique identifier for the patient (e.g., Firebase user ID)
    val name: String, // Name of the patient
    val roomNumber: String // Room number where the patient is located
)