package com.example.wpms.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wpms.Entities.Caregiver

@Dao
interface CaregiverDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCaregiver(caregiver: Caregiver)

        @Update
        suspend fun updateCaregiver(caregiver: Caregiver)

        @Delete
        suspend fun deleteCaregiver(caregiver: Caregiver)

        @Query("SELECT * FROM CAREGIVERS ORDER BY id DESC")
        fun getAllCaregivers(): LiveData<List<Caregiver>>

        @Query("SELECT * FROM CAREGIVERS WHERE medicalId LIKE :query OR id LIKE :query")
        fun searchCaregiver(query: Int?): LiveData<List<Caregiver>>
}