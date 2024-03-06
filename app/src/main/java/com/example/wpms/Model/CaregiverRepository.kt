package com.example.wpms.Model


class CaregiverRepository(private val db: WpmsDB) {
    suspend fun insertCaregiver(caregiver: Caregiver) = db.getCaregiverDao().insertCaregiver(caregiver)
    suspend fun deleteCaregiver(caregiver: Caregiver) = db.getCaregiverDao().deleteCaregiver(caregiver)
    suspend fun updateCaregiver(caregiver: Caregiver) = db.getCaregiverDao().updateCaregiver(caregiver)

    fun getAllCaregivers() = db.getCaregiverDao().getAllCaregivers()
//    fun searchCaregiver(query: Int?) = db.getCaregiverDao().searchCaregiver(query)
}