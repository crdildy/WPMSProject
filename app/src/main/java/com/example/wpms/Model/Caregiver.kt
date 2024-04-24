package com.example.wpms.Model

data class Caregiver(
    val userId: String = "", // Unique identifier for the user (e.g., Firebase user ID)
    val name: String = "", // Name of the
    val patients: List<String> = emptyList() // List of patients assigned to the caregiver (userId)
)