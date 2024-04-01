package com.example.wpms

import android.app.Application
import com.example.wpms.DAOs.PatientDatabase
import com.example.wpms.repository.PressureDataRepo
import com.example.wpms.DAOs.PressureDataDao

class wpmsApplication : Application(){
    //uses lazy so the database and the repositories are only created when they are used
    //rather than when the application starts
    val database by lazy { PatientDatabase.getDatabase(this) }
    val repository by lazy { PressureDataRepo(database.getPressureDataDao()) }
}