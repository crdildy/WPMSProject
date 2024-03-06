package com.example.wpms.Model
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "caregivers_table")
data class Caregiver(
    @PrimaryKey(autoGenerate = true)
    val caregiver_id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val num_patients: Int
)
