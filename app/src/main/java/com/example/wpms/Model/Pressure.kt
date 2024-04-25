package com.example.wpms.Model

import com.google.firebase.Timestamp

data class Pressure(
    val userId: String = "", // Unique identifier for the user (e.g., Firebase user ID)
    val pressureCenter: Float, // Pressure value at the center
    val pressureLeft: Float, // Pressure value at the left side
    val pressureRight: Float, // Pressure value at the right side
    val timestamp: Timestamp = Timestamp.now() // Timestamp of the pressure reading
)