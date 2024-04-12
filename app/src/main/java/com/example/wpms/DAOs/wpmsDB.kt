package com.example.wpms.DAOs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wpms.Entities.Breach
import com.example.wpms.Entities.Caregiver
import com.example.wpms.Entities.Device
import com.example.wpms.Entities.Patient
import com.example.wpms.Entities.PressureData

//Room database, stored locally
@Database(entities = [Patient::class, Caregiver::class, Device::class, Breach::class, PressureData::class], version = 1)
abstract class PatientDatabase: RoomDatabase() {
    abstract fun getPatientDao(): PatientDao
    abstract fun getCaregiverDao(): CaregiverDao
    abstract fun getDeviceDao(): DeviceDao
    abstract fun getBreachDao(): BreachDao

    companion object{
        @Volatile
        private var instance: PatientDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PatientDatabase::class.java,
                 "wpms_db"
        ).build()
    }
}