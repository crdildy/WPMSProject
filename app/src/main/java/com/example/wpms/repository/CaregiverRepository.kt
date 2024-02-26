package com.example.wpms.repository

import com.example.wpms.database.PatientDatabase
import com.example.wpms.model.Caregiver


class CaregiverRepository(private val db: PatientDatabase) {
    suspend fun insertCaregiver(caregiver: Caregiver) = db.getCaregiverDao().insertCaregiver(caregiver)
    suspend fun deleteCaregiver(caregiver: Caregiver) = db.getCaregiverDao().deleteCaregiver(caregiver)
    suspend fun updateCaregiver(caregiver: Caregiver) = db.getCaregiverDao().updateCaregiver(caregiver)

    fun getAllCaregivers() = db.getCaregiverDao().getAllCaregivers()
    fun searchCaregiver(query: Int?) = db.getCaregiverDao().searchCaregiver(query)
}