package com.example.wpms.Model

data class User(
    val userId: String = "", // Unique identifier for the user (e.g., Firebase user ID)
    val name: String = "", // Name of the user
    val role: String = "", // Role of user as either caregiver or patient
    val profilePictureUrl: String = "" // URL of the profile picture
)