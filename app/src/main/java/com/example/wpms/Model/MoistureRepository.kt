package com.example.wpms.Model

class MoistureRepository(private val db: WpmsDB) {
    suspend fun insertMoisture(moisture: Moisture) = db.getMoistureDao().insertMoisture(moisture)
    suspend fun deleteMoisture(moisture: Moisture) = db.getMoistureDao().deleteMoisture(moisture)
    suspend fun updateMoisture(moisture: Moisture) = db.getMoistureDao().updateMoisture(moisture)
    suspend fun getAllMoisture() = db.getMoistureDao().getAllMoistureData()
}