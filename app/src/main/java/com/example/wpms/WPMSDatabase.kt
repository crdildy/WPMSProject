package com.example.wpms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//@Database(entities = [Patient::class, Caregiver::class, Device::class, Breach::class, PressureData::class, MoistureData::class], version = 1, exportSchema = false)
@Database(entities = [PressureData::class], version = 1, exportSchema = false)
public abstract class WPMSDatabase : RoomDatabase()
{
    //Getters for all DAOs
//    abstract fun getPatientDao(): PatientDao
//    abstract fun getCaregiverDao(): CaregiverDao
//    abstract fun getDeviceDao(): DeviceDao
//    abstract fun getBreachDao(): BreachDao
    abstract fun getPressureDataDao(): PressureDataDao
//    abstract fun getMoistureDataDao(): MoistureDataDao

    companion object{
        @Volatile
        private var INSTANCE: WPMSDatabase? = null
        fun getDatabase(context: Context): WPMSDatabase{
            return INSTANCE?: synchronized(this)
                {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WPMSDatabase::class.java,
                        "wpms_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    instance
                }
        }
    }
}
//    private class PatientDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var pressureDataDao = database.getPressureDataDao()
//
//                    // Delete all content here.
//                    //pressureDataDao.deleteAll()
//
//                    // Add sample words.
//                    var pressureData = PressureData(36.35, 1111, 328051)
//                    pressureDataDao.insert(pressureData)
//
//                    pressureData = PressureData(100.00, 2222, 324325)
//                    pressureDataDao.insert(pressureData)
//
//                }
//            }
//        }
//    }
//    companion object{
//        @Volatile
////        private var instance: PatientDatabase? = null
////        private val LOCK = Any()
////
////        operator fun invoke(context: Context) = instance ?:
////        synchronized(LOCK){
////            instance ?:
////            createDatabase(context).also{
////                instance = it
////            }
//        private var INSTANCE: PatientDatabase? = null
//
//        fun getDatabase(
//            context: Context,
//            scope: CoroutineScope
//        ): PatientDatabase {
//            //if INSTANCE IS NOT NULL, THEN RETURN IT ELSE CREATE THE DATABASE
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    PatientDatabase::class.java,
//                    "wpms_db"
//                )
//                    .addCallback(PatientDatabaseCallback(scope))
//                    .build()
//                INSTANCE = instance
//                //returns instance
//                instance
//            }
//      }
//
////        private fun createDatabase(context: Context) =
////            Room.databaseBuilder(
////                context.applicationContext,
////                PatientDatabase::class.java,
////                 "wpms_db"
////        ).build()
//    }
//}