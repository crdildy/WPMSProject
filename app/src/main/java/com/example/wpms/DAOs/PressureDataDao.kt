package com.example.wpms.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wpms.Entities.PressureData

@Dao
interface PressureDataDao {
    @Insert
    suspend fun insert(pressureData: PressureData)

    @Query("SELECT * FROM pressure_data ORDER BY timestamp DESC")
    suspend fun getAllPressureData(): List<PressureData>
}