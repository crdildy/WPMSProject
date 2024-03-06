package com.example.wpms.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices_table")
data class Device(
    @PrimaryKey(autoGenerate = true)
    val device_id: Int,
    val status: String,
    val num_breaches: Int
)
