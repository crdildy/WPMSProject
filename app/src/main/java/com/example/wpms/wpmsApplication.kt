package com.example.wpms

import android.app.Application
import com.example.wpms.DAOs.PatientDatabase
import com.example.wpms.repository.PressureDataRepo
import androidx.room.Database
import com.example.wpms.DAOs.PressureDataDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class wpmsApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())


    //uses lazy so the database and the repositories are only created when they are used
    //rather than when the application starts
    val database by lazy { PatientDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PressureDataRepo(database.getPressureDataDao()) }
}