package com.example.wpms.Entities
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Caregivers")
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
