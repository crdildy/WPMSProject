package com.example.wpms

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MoistureDataRepository(private val moistureDataDao: MoistureDataDao){

    val allMoistureData: Flow<List<MoistureData>> = moistureDataDao.getAllMoistureData()

    @WorkerThread
    suspend fun insertMoistureData(moistureData: MoistureData){
        moistureDataDao.insertMoistureData(moistureData)
    }

}