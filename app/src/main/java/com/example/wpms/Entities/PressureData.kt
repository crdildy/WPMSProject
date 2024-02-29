package com.example.wpms.Entities

import androidx.room.Entity

@Entity(tableName = "pressure_data")
data class PressureData(
    val pressureValue: Float,
    val timestamp: Long
)
