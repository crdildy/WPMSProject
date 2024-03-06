package com.example.wpms.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Patient::class, Caregiver::class, Device::class, Breach::class, Pressure::class, Moisture::class], version = 2)
abstract class WpmsDB: RoomDatabase() {

    abstract fun getPatientDao(): PatientDao
    abstract fun getCaregiverDao(): CaregiverDao
    abstract fun getDeviceDao(): DeviceDao
    abstract fun getBreachDao(): BreachDao
    abstract fun getPressureDao(): PressureDao
    abstract fun getMoistureDao(): MoistureDao
    companion object{
        private const val OLD_version = 1
        private const val NEW_version = 2

        private val MIGRATION_1_2: Migration = object : Migration(OLD_version, NEW_version){
            override fun migrate(db: SupportSQLiteDatabase) {
                TODO("Not yet implemented")
//                example of how to update the tables
//                db.execSQL("ALTER TABLE YourTable ADD COLUMN newColumn TEXT")
                db.execSQL("ALTER TABLE patients_table ADD COLUMN id INT")

            }
        }
        @Volatile
        private var instance: WpmsDB? = null
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
                WpmsDB::class.java,
                 "wpms_db"
        ).build()
    }
}