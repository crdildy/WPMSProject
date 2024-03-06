package com.example.wpms.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PressureDao {
    @Insert
    suspend fun insertPressure(pressureData: Pressure)
    @Delete
    suspend fun deletePressure(pressureData: Pressure)
    @Update
    suspend fun updatePressure(pressureData: Pressure)
    @Query("SELECT * FROM pressure_table")
    suspend fun getAllPressureData(): List<Pressure>
//    @Query("SELECT * FROM pressure_table ORDER BY timestamp DESC")
//    suspend fun getAllPressureData(): List<Pressure>
//
//    @Query("SELECT * FROM pressure_table WHERE patientId = :patientId")
//    suspend fun getPressureByPatientId(patientId: Long): LiveData<List<Pressure>>
}
