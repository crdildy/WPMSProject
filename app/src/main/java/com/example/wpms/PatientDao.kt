package com.example.wpms

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//@Dao
//interface PatientDao {
//    //@Insert(onConflict = OnConflictStrategy.REPLACE)
//    //suspend fun insertPatient(patient: Patient)
//    @Insert
//    suspend fun insert(patient: Patient)
//
//    @Update
//    suspend fun updatePatient(patient: Patient)
//
//    @Delete
//    suspend fun deletePatient(patient: Patient)
//
//    @Query("SELECT * FROM PATIENTS ORDER BY id DESC")
//    fun getAllPatients(): Flow<List<Patient>>
//    //fun getAllPatients(): LiveData<List<Patient>>
//
//    @Query("SELECT * FROM PATIENTS WHERE firstName LIKE :query OR lastName LIKE :query")
//    fun searchPatient(query: String?): LiveData<List<Patient>>
//
//}