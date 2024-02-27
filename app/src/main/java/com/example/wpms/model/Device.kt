package com.example.wpms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Devices")
data class Device(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val patientID: Int,
    val deviceId: Int,
    val status: Boolean,
    val numbOfPressureBreaches: Int,
    val numOfMoistureBreaches: Int,

)
