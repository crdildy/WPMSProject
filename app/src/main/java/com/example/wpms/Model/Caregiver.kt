package com.example.wpms.Model
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "caregivers_table")
data class Caregiver(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val medicalId: Int,
    val dob : String,
    val email: String,
    val numPatients: Int
)
