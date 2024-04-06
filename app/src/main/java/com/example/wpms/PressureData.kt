package com.example.wpms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.example.wpms.Patient
import java.time.format.DateTimeFormatter

//@Entity(tableName = "pressure_data_table" , foreignKeys = [
//    //parent column is used for the foreign entiity id, child column is used for THIS entity id
//    //add ", onDelete = CASCADE, onUpdate = CASCADE"
//    ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"]),
//])
@Entity(tableName = "pressure_data_table")
data class PressureData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "Pressure") var pressure: Int
//    @ColumnInfo(name = "Date") var dateString: String?,
//    @ColumnInfo(name = "Time") var timeString: String?
//    @ColumnInfo("Patient ID") var patientId: Int = 0
){
    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}

