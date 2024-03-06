package com.example.wpms.Model

class PressureRepository(private val db: WpmsDB) {
    suspend fun insertPressure(pressure: Pressure) = db.getPressureDao().insertPressure(pressure)
    suspend fun deletePressure(pressure: Pressure) = db.getPressureDao().deletePressure(pressure)
    suspend fun updatePressure(pressure: Pressure) = db.getPressureDao().updatePressure(pressure)
    suspend fun getAllPressure() = db.getPressureDao().getAllPressureData()
}