package com.example.wpms.Model

class PressureRepository(private val pressureDataDao: PressureDao) {
    suspend fun insertPressureDao(pressureData: Pressure){
        pressureDataDao.insert(pressureData)
    }

    suspend fun getAllPressureData() = pressureDataDao.getAllPressureData()

//    suspend fun getPressureByPatientId(query: Long) = pressureDataDao.getPressureByPatientId(query)
}