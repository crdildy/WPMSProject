package com.example.wpms.Model

import com.google.firebase.Timestamp

data class Moisture(
    val userId: String = "", // Unique identifier for the user (e.g., Firebase user ID)
    val isMoist: Boolean = false, // Moisture status of the patient
    val timestamp: Timestamp = Timestamp.now() // Timestamp of the moisture reading
)