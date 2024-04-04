package com.example.wpms

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PressureDataDao {
    @Query("SELECT * FROM pressure_data_table ORDER BY id ASC")
    fun getAllPressureData(): Flow<List<PressureData>>

    @Insert
    suspend fun insertPressureData(pressureData: PressureData)

    @Delete
    suspend fun deletePressureData(pressureData: PressureData)


    //Probably wont need this anymore
//    @Query("SELECT * FROM pressure_data ORDER BY timestamp DESC")
//    fun getAllPressureData(): Flow<List<PressureData>>
//
//    //will have to change livedata to flow
//    @Query("SELECT * FROM pressure_data WHERE patientId = :patientId")
//    suspend fun getPressureByPatientId(patientId: Long): LiveData<List<PressureData>>
}
