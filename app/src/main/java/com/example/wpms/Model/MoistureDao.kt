package com.example.wpms.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoistureDao {
    @Insert
    suspend fun insertMoisture(moisture: Moisture)
    @Delete
    suspend fun deleteMoisture(moisture: Moisture)
    @Update
    suspend fun updateMoisture(moisture: Moisture)
    @Query("SELECT * FROM moisture_table")
    suspend fun getAllMoistureData(): List<Moisture>
//    @Query("SELECT * FROM moisture_table ORDER BY timestamp DESC")
//    suspend fun getAllMoistureData(): List<Moisture>
}