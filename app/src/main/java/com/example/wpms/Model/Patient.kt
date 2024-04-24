package com.example.wpms.Model

data class Patient(
    val userId: String = "", // Unique identifier for the user (e.g., Firebase user ID)
    val name: String = "", // Name of the user
    val room: String = "", // Room number of the patient
    val caregivers: List<String> = emptyList()
)