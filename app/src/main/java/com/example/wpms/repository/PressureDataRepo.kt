package com.example.wpms.repository

import com.example.wpms.DAOs.PressureDataDao
import com.example.wpms.Entities.PressureData

class PressureDataRepo(private val pressureDataDao: PressureDataDao) {
    suspend fun insertPressureDao(pressureData: PressureData){
        pressureDataDao.insert(pressureData)
    }

    suspend fun getAllPressureData() = pressureDataDao.getAllPressureData()
}