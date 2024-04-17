package com.example.wpms.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Patients", foreignKeys = [
    //parent column is used for the foreign entiity id, child column is used for THIS entity id
    //add ", onDelete = CASCADE, onUpdate = CASCADE"
    ForeignKey(entity = Caregiver::class, parentColumns = ["id"], childColumns = ["caregiverId"]),
])
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val dob : String,
    val deviceId: Int,
    val caregiverID: Int,
    val email: String
)
