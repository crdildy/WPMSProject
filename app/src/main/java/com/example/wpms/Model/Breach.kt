package com.example.wpms.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.DateTime

@Entity(tableName = "breaches_table")
data class Breach(
    @PrimaryKey(autoGenerate = true)
    val device_id: Int,
    val time: DateTime,
    val pressure_val: Int,
    val moisture_val: Boolean
)
