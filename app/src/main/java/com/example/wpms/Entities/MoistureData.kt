package com.example.wpms.Entities

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "moisture_data" , foreignKeys = [
    //parent column is used for the foreign entiity id, child column is used for THIS entity id
    //add ", onDelete = CASCADE, onUpdate = CASCADE"
    ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"]),
])
data class MoistureData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val moistureValue: Boolean,
    val timestamp: Long,
    val patientId: Long)
