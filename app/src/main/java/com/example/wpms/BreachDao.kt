package com.example.wpms

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

//@Dao
//interface BreachDao {
//
//        @Insert(onConflict = OnConflictStrategy.REPLACE)
//        suspend fun insertBreach(breach: Breach)
//
//        @Update
//        suspend fun updateBreach(breach: Breach)
//
//        @Delete
//        suspend fun deleteBreach(breach: Breach)
//
//        @Query("SELECT * FROM BREACHES ORDER BY id DESC")
//        fun getAllBreaches(): LiveData<List<Breach>>
//
//        @Query("SELECT * FROM BREACHES WHERE time LIKE :query OR date LIKE :query")
//        fun searchBreach(query: Int?): LiveData<List<Breach>>
//}