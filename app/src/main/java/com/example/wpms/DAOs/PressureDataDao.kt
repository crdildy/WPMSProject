package com.example.wpms.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wpms.Entities.PressureData

@Dao
interface PressureDataDao {
    @Insert
    suspend fun insert(pressureData: PressureData)

    //Probably wont need this anymore
    @Query("SELECT * FROM pressure_data ORDER BY timestamp DESC")
    suspend fun getAllPressureData(): List<PressureData>

    @Query("SELECT * FROM pressure_data WHERE patientId = :patientId")
    suspend fun getPressureByPatientId(patientId: Long): LiveData<List<PressureData>>
}
