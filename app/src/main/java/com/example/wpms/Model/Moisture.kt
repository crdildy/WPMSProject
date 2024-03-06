package com.example.wpms.Model

import androidx.room.PrimaryKey
import androidx.room.Entity
import com.google.type.DateTime

@Entity(tableName = "moisture_table")
data class Moisture(
    @PrimaryKey(autoGenerate = true)
    val patient_id: Int,
    val device_id: Int,
    val isWet: Boolean,
    val time: DateTime
)
