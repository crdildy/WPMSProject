package com.example.wpms.repository

import com.example.wpms.DAOs.PatientDatabase
import com.example.wpms.DAOs.PatientDao
import com.example.wpms.Entities.Patient
import com.example.wpms.Entities.PressureData
import kotlinx.coroutines.flow.Flow


//class PatientRepository(private val db: PatientDatabase){
class PatientRepository(private val patientDao: PatientDao) {
    val allPatients: Flow<List<Patient>> = patientDao.getAllPatients()

    //suspend fun insertPatient(patient: Patient) = db.getPatientDao().insertPatient(patient)
    suspend fun insert(patient: Patient){
        patientDao.insert(patient)
    }
    //suspend fun deletePatient(patient: Patient) = db.getPatientDao().deletePatient(patient)
    //suspend fun updatePatient(patient: Patient) = db.getPatientDao().updatePatient(patient)

    //fun getAllPatients() = db.getPatientDao().getAllPatients()
    suspend fun getAllPatients() = patientDao.getAllPatients()
    //fun searchPatient(query: String?) = db.getPatientDao().searchPatient(query)


}