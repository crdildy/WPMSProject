package com.example.wpms.DAOs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wpms.Entities.Breach
import com.example.wpms.Entities.Caregiver
import com.example.wpms.Entities.Device
import com.example.wpms.Entities.History
import com.example.wpms.Entities.Patient
import com.example.wpms.Entities.PressureData


@Database(entities = [Patient::class, Caregiver::class, Device::class, Breach::class, History::class, PressureData::class], version = 1)
abstract class PatientDatabase: RoomDatabase() {
    abstract fun getPatientDao(): PatientDao
    abstract fun getCaregiverDao(): CaregiverDao
    abstract fun getDeviceDao(): DeviceDao
    abstract fun getBreachDao(): BreachDao

    abstract fun getHistoryDao(): HistoryDao

    abstract fun getPressureDataDao(): PressureDataDao


    companion object{
        @Volatile
//        private var instance: PatientDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?:
//        synchronized(LOCK){
//            instance ?:
//            createDatabase(context).also{
//                instance = it
//            }
        private var INSTANCE: PatientDatabase? = null

        fun getDatabase(context: Context): PatientDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PatientDatabase::class.java,
                    "wpms_db"
                ).build()
                INSTANCE = instance
                instance
            }
      }

//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                PatientDatabase::class.java,
//                 "wpms_db"
//        ).build()
    }
}