package com.example.wpms.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "breaches_table")
data class Breach(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val breachNum: Int,
    val time: Int,
    val date: Int,
    val pressureMeasure: Int,
    val moisturePresence: Boolean

)
