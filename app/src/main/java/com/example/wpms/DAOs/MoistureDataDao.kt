package com.example.wpms.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wpms.Entities.MoistureData
@Dao
interface MoistureDataDao {
    @Insert
    suspend fun insert(moistureData: MoistureData)

    @Query("SELECT * FROM pressure_data ORDER BY timestamp DESC")
    suspend fun getAllMoistureData(): List<MoistureData>
}