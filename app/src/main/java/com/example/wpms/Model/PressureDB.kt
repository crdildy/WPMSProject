package com.example.wpms.Model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wpms.DAOs.PressureDataDao
import com.example.wpms.Entities.PressureData

@Database(entities = [PressureData::class], version = 1)
abstract class PressureDB : RoomDatabase(){
    abstract fun pressureDataDao(): PressureDataDao
}