package com.example.wpms

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
//import androidx.room.ForeignKey
import java.time.format.DateTimeFormatter
//
@Entity(tableName = "moisture_data_table")
//, foreignKeys = [
//    //parent column is used for the foreign entiity id, child column is used for THIS entity id
//    //add ", onDelete = CASCADE, onUpdate = CASCADE"
//    ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"]),
//])
data class MoistureData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo("Moisture") var moisture: Int,
//    @ColumnInfo("Date") var dateString: String?,
//    @ColumnInfo(name = "Time") var timeString: String?,
//    @ColumnInfo("Patient ID") var patientId: Int = 0
)
