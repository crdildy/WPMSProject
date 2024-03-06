package com.example.wpms.Model

class PatientRepository(private val db: WpmsDB){

    suspend fun insertPatient(patient: Patient) = db.getPatientDao().insertPatient(patient)
    suspend fun deletePatient(patient: Patient) = db.getPatientDao().deletePatient(patient)
    suspend fun updatePatient(patient: Patient) = db.getPatientDao().updatePatient(patient)

    fun getAllPatients() = db.getPatientDao().getAllPatients()
//    fun searchPatient(query: String?) = db.getPatientDao().searchPatient(query)
}