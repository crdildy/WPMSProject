package com.example.wpms.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wpms.model.Patient
@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("SELECT * FROM PATIENTS ORDER BY id DESC")
    fun getAllPatients(): LiveData<List<Patient>>

    @Query("SELECT * FROM PATIENTS WHERE firstName LIKE :query OR lastName LIKE :query")
    fun searchPatient(query: String?): LiveData<List<Patient>>

}