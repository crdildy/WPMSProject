package com.example.wpms

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

//@Dao
//interface DeviceDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDevice(device: Device)
//
//    @Update
//    suspend fun updateDevice(device: Device)
//
//    @Delete
//    suspend fun deleteDevice(device: Device)
//
//    @Query("SELECT * FROM DEVICES ORDER BY id DESC")
//    fun getAllDevices(): LiveData<List<Device>>
//
//    @Query("SELECT * FROM DEVICES WHERE id LIKE :query OR deviceId LIKE :query")
//    fun searchDevice(query: Int?): LiveData<List<Device>>
//}