package com.example.wpms

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PressureRepository(private val pressureDataDao: PressureDataDao) {

    val allPressureData: Flow<List<PressureData>> = pressureDataDao.getAllPressureData()

    @WorkerThread
    suspend fun insertPressureData(pressureData: PressureData){
        pressureDataDao.insertPressureData(pressureData)
    }
//    suspend fun insert(pressureData: PressureData){
//        pressureDataDao.insert(pressureData)
//    }
//
//    suspend fun getAllPressureData() = pressureDataDao.getAllPressureData()
//
//    suspend fun getPressureByPatientId(query: Long) = pressureDataDao.getPressureByPatientId(query)
}