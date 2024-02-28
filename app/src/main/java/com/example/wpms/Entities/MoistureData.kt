package com.example.wpms.Entities

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "moisture_data")
data class MoistureData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val moistureValue: Boolean,
    val timestamp: Long)
