package com.example.wpms.repository

import com.example.wpms.DAOs.PatientDatabase
import com.example.wpms.Entities.Patient
class PatientRepository(private val db: PatientDatabase){

    suspend fun insertPatient(patient: Patient) = db.getPatientDao().insertPatient(patient)
    suspend fun deletePatient(patient: Patient) = db.getPatientDao().deletePatient(patient)
    suspend fun updatePatient(patient: Patient) = db.getPatientDao().updatePatient(patient)

    fun getAllPatients() = db.getPatientDao().getAllPatients()
    fun searchPatient(query: String?) = db.getPatientDao().searchPatient(query)
}