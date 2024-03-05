package com.example.wpms.Entities

import android.text.style.ForegroundColorSpan
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "pressure_data" , foreignKeys = [
    //parent column is used for the foreign entiity id, child column is used for THIS entity id
    //add ", onDelete = CASCADE, onUpdate = CASCADE"
    ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"]),
    ForeignKey(entity = Caregiver::class, parentColumns = ["id"], childColumns = ["caregiverId"]),
])
data class PressureData(
    val pressureValue: Float,
    val timestamp: Long,
    val patientId: Long

)
