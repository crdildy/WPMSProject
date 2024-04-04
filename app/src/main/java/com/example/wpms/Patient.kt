package com.example.wpms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
//import com.example.wpms.Caregiver

//@Entity(tableName = "patient_table", foreignKeys = [
//    //parent column is used for the foreign entiity id, child column is used for THIS entity id
//    //add ", onDelete = CASCADE, onUpdate = CASCADE"
//    ForeignKey(entity = Caregiver::class, parentColumns = ["id"], childColumns = ["caregiverId"]),
//])
//data class Patient(
//    @PrimaryKey(autoGenerate = true) var id: Int = 0,
//    @ColumnInfo("First name") var firstName: String,
//    @ColumnInfo("Last name") var lastName: String,
//    @ColumnInfo("Caregiver ID") var caregiverID: Int
////    val dob : String,
////    val deviceId: Int,
////    val caregiverID: Int,
////    val email: String
//)
