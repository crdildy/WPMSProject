package com.example.wpms.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(tableName = "Breaches" , foreignKeys = [
    //parent column is used for the foreign entiity id, child column is used for THIS entity id
    //add ", onDelete = CASCADE, onUpdate = CASCADE"
    ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"]),
])
data class Breach(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val breachNum: Int,
    val time: Int,
    val date: Int,
    val pressureMeasure: Int,
    val moisturePresence: Boolean,
    val patientId: Long

)
