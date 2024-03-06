package com.example.wpms.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CaregiverDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCaregiver(caregiver: Caregiver)

        @Update
        suspend fun updateCaregiver(caregiver: Caregiver)

        @Delete
        suspend fun deleteCaregiver(caregiver: Caregiver)

        @Query("SELECT * FROM caregivers_table ORDER BY id DESC")
        fun getAllCaregivers(): LiveData<List<Caregiver>>

        @Query("SELECT * FROM caregivers_table WHERE medicalId LIKE :query OR id LIKE :query")
        fun searchCaregiver(query: Int?): LiveData<List<Caregiver>>
}