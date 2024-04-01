package com.example.wpms.repository

import com.example.wpms.DAOs.PressureDataDao
import com.example.wpms.Entities.PressureData
import kotlinx.coroutines.flow.Flow
import com.example.wpms.DAOs.PatientDatabase

class PressureDataRepo(private val pressureDataDao: PressureDataDao) {

    val allPressure: Flow<List<PressureData>> = pressureDataDao.getAllPressureData()

    suspend fun insert(pressureData: PressureData){
        pressureDataDao.insert(pressureData)
    }

    suspend fun getAllPressureData() = pressureDataDao.getAllPressureData()

    suspend fun getPressureByPatientId(query: Long) = pressureDataDao.getPressureByPatientId(query)
}