package com.example.wpms.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.google.type.Date
import java.util.Date

@Entity(tableName = "patients_table")
data class Patient (
    @PrimaryKey(autoGenerate = true)
    val patient_id: Int,
    val first_name: String,
    val last_name: String,
    val dob : Date,
    val email: String,
    val device_id: Int,
    val caregiver_id: Int
)