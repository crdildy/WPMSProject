package com.example.wpms.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wpms.model.Patient
import com.example.wpms.model.Caregiver
import com.example.wpms.model.Device
import com.example.wpms.model.Breach


@Database(entities = [Patient::class, Caregiver::class, Device::class, Breach::class], version = 1)
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