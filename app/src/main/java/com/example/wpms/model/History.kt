package com.example.wpms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class History {
    @Entity(tableName = "History")
    data class Device(
        @PrimaryKey(autoGenerate = true)
        val id : Int,
        val date: Int,
        val time : Int,
        //this might change depending on the type of value we get
        val leftPressure: Int,
        val rightPressure: Int,
        val middlePressure: Int,
        //dont think we need this but it's mentioned in final proposal
        val deviceID: Int,

    )
}