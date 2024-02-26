package com.example.wpms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Patients")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val dob : String,
    val deviceId: Int,
    val careGiverID: Int,
    val email: String
)
