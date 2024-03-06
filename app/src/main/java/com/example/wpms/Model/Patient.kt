package com.example.wpms.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "patients_table")
data class Patient (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val dob : String,
    val email: String,
    val device_id: String,
    val caregiver_id: String
)