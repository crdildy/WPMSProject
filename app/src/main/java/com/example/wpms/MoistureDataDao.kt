package com.example.wpms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow


@Dao
interface MoistureDataDao {
    @Query("SELECT * FROM moisture_data_table ORDER BY id DESC")
    fun getAllMoistureData(): Flow<List<MoistureData>>
    @Insert
    suspend fun insertMoistureData(moistureData: MoistureData)

    @Delete
    suspend fun deleteMoistureData(moistureData: MoistureData)


}