package com.example.wpms.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoistureDao {
    @Insert
    suspend fun insert(moistureData: Moisture)

    @Query("SELECT * FROM moisture_table ORDER BY timestamp DESC")
    suspend fun getAllMoistureData(): List<Moisture>
}