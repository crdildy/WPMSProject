package com.example.wpms.Model

data class Patient(
    val userId: String = "", // Unique identifier for the user (e.g., Firebase user ID)
    val name: String = "", // Name of the user
    val room: String = "", // Room number of the patient
    val isBreach: Boolean = false, // Indicates if the patient has breached a threshold
    val caregivers: List<String> = emptyList()
)