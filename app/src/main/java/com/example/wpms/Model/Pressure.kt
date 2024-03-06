package com.example.wpms.Model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "pressure_table" , foreignKeys = [
    //parent column is used for the foreign entiity id, child column is used for THIS entity id
    //add ", onDelete = CASCADE, onUpdate = CASCADE"
    ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"]),
    ForeignKey(entity = Caregiver::class, parentColumns = ["id"], childColumns = ["caregiverId"]),
])
data class Pressure(
    val pressureValue: String,
//    val timestamp: Long,
//    val patientId: Long
)
