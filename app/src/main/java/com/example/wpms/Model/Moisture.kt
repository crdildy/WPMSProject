package com.example.wpms.Model

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "moisture_table")
data class Moisture(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val moistureValue: Boolean,
    val timestamp: Long)
