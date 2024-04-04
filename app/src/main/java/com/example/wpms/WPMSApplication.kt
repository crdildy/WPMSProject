package com.example.wpms

import android.app.Application

class WPMSApplication : Application(){
//    val applicationScope = CoroutineScope(SupervisorJob())
    //uses lazy so the database and the repositories are only created when they are used
    //rather than when the application starts
//    val database by lazy { PatientDatabase.getDatabase(this, applicationScope) }
//    val repository by lazy { PressureDataRepo(database.getPressureDataDao()) }

    private val database by lazy { WPMSDatabase.getDatabase(this) }
    val pressureRepository by lazy { PressureRepository(database.getPressureDataDao())}
    //Need a repository declared for each Entity
}