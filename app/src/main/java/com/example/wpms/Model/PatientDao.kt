package com.example.wpms.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("SELECT * FROM patients_table ORDER BY id DESC")
    fun getAllPatients(): LiveData<List<Patient>>

    @Query("SELECT * FROM patients_table WHERE firstName LIKE :query OR lastName LIKE :query")
    fun searchPatient(query: String?): LiveData<List<Patient>>

}