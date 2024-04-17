package com.example.wpms.Model

data class User(
    val userId: String, // Unique identifier for the user (e.g., Firebase user ID)
    val name: String, // Name of the user
    val roomNumber: String, // Room number where the user is located
    val role: String // Role of user as either caregiver or patient
)