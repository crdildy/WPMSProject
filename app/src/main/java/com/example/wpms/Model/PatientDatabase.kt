package com.example.wpms.Model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wpms.DAOs.PatientDao
import com.example.wpms.Entities.Patient
import kotlinx.coroutines.CoroutineScope


//Not sure if need to list out all of those classes
@Database(entities = [Patient::class], version = 1)
//    , Caregiver::class, Device::class, Breach::class, History::class], version = 1)
abstract class PatientDatabase: RoomDatabase() {

    abstract fun patientDao() : PatientDao
//    abstract fun getPatientDao(): PatientDao
//    abstract fun getCaregiverDao(): CaregiverDao
//    abstract fun getDeviceDao(): DeviceDao
//    abstract fun getBreachDao(): BreachDao
//
//    abstract fun getHistoryDao(): HistoryDao

    private class PatientDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

    }
    companion object{
        @Volatile
        private var instance: PatientDatabase? = null
//        private val LOCK = Any()

//        operator fun invoke(context: Context) = instance ?:
//        synchronized(LOCK){
//            instance ?:
//            createDatabase(context).also{
//                instance = it
//            }

        }

//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                PatientDatabase::class.java,
//                 "wpms_db"
//        ).build()
//    }
}