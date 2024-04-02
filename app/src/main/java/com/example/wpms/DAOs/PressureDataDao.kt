package com.example.wpms.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wpms.Entities.PressureData
import kotlinx.coroutines.flow.Flow

@Dao
interface PressureDataDao {
    @Insert
    suspend fun insert(pressureData: PressureData)

    //Probably wont need this anymore
    @Query("SELECT * FROM pressure_data ORDER BY timestamp DESC")
    fun getAllPressureData(): Flow<List<PressureData>>

    //will have to change livedata to flow
    @Query("SELECT * FROM pressure_data WHERE patientId = :patientId")
    suspend fun getPressureByPatientId(patientId: Long): LiveData<List<PressureData>>
}
