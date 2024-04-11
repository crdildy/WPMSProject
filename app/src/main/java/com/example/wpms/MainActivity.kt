package com.example.wpms

// this application will act as the client
// Client will connect to the TCP server running the simulating data program
// Use socket class to establish a connection
// Once connection is establish, read the data being sent

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import java.io.IOException



var mediaPlayer : MediaPlayer? = null

class MainActivity : AppCompatActivity() {
    private lateinit var database: WPMSDatabase
    private lateinit var dataHandler: DataHandler
    //private lateinit var pressureDataViewModel: PressureDataViewModel
    //private lateinit var pressureDB: PressureDB //declare db instance for pressure

    //private val pressureDataViewModel: PressureDataViewModel by viewModels {
    //    PressureDataViewModelFactory((application as wpmsApplication).repository)
    //}

//    private val patientViewModel: PatientViewModel by viewModels {
//        PatientViewModelFactory((application as WPMSApplication).repository)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //connects recyclerview
        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //val adapter = PressureListAdapter()
        //recyclerView.adapter = adapter
        //ecyclerView.layoutManager = LinearLayoutManager(this)

        //observes the live data in patient viewmodel
//        PatientViewModel.allPressure.observe(owner = this) { pressures ->
//            // Update the cached copy of the words in the adapter.
//            pressures.let { adapter.submitList(it) }
//        }


        //initialize Room database for pressure
        //pressureDB = Room.databaseBuilder(
        //  applicationContext,
        //  PressureDB::class.java, "pressure_db"
        //).build()


        database = Room.databaseBuilder(
            applicationContext,
            WPMSDatabase::class.java,
            "wpms_database"
        ).fallbackToDestructiveMigration()
            .build()


        // Initialize DataHandler
        dataHandler = DataHandler.getInstance()

        // Start data retrieval
        dataHandler.startDataRetrieval()

        //initialize the pressure data view model
        //val pressureRepository = PressureDataRepo(pressureDB.pressureDataDao())
        //pressureDataViewModel =
        //      ViewModelProvider(this, PressureDataViewModelFactory(pressureRepository))
        //      .get(PressureDataViewModel::class.java)

        //displaying incoming data from simulation
        val textView = findViewById<View>(R.id.textView1) as TextView
        val textView2 = findViewById<View>(R.id.textView2) as TextView
        val textView3 = findViewById<View>(R.id.textView3) as TextView
        val textView4 = findViewById<View>(R.id.textView4) as TextView
        val textView5 = findViewById<View>(R.id.textView5) as TextView
        val textView6 = findViewById<View>(R.id.textView6) as TextView

        // Initialization variables to store threshold
        val pressureThreshold = 65

        dataHandler.observeData().observe(this, Observer { dataList ->
            Log.d("MainActivity", "Updating UI with data: $dataList")
            // Update your UI here
            if (dataList.isNotEmpty()) {
                val moistureVal = dataList[0]
                Log.d("MainActivity", "Moisture: $moistureVal")
                val pressureOneVal = dataList.getOrNull(1) ?: 0
                Log.d("MainActivity", "P1: $pressureOneVal")
                val pressureTwoVal = dataList.getOrNull(2) ?: 0
                Log.d("MainActivity", "P2: $pressureTwoVal")
                val pressureThreeVal = dataList.getOrNull(3) ?: 0
                Log.d("MainActivity", "P3: $pressureThreeVal")

                // Update TextViews with the received data
                textView2.text = "Pressure level on sensor one: $pressureOneVal"
                textView3.text = "Pressure level on sensor two: $pressureTwoVal"
                textView4.text = "Pressure level on sensor three: $pressureThreeVal"
                textView5.text =
                    if (pressureOneVal > pressureThreshold || pressureTwoVal > pressureThreshold || pressureThreeVal > pressureThreshold) {
                        "Pressure Levels Exceed Threshold!"
                        // You can also call playAudio() here if you want to play the alarm sound
                    } else {
                        "Pressure Levels are Okay!"
                    }
                textView6.text = if (moistureVal == 1) {
                    "Moisture Detected: True"
                } else {
                    "Moisture Detected: False"
                }

                // Insert the pressure data into the database
                insertPressure(pressureOneVal)
            }
        })
    }

    private fun insertPressure(pressureData: Int){
        GlobalScope.launch(Dispatchers.IO) {
            //pressureDB.pressureDataDao().insert(pressureData)
            val pressureEntity = PressureData(pressure = pressureData)
            database.getPressureDataDao().insertPressureData(pressureEntity)
        }
    }

    private suspend fun checkDataFromDB(): Flow<List<PressureData>> {
        return withContext(Dispatchers.IO){
            database.getPressureDataDao().getAllPressureData()
        }
    }
//private suspend fun checkDataFromDB(): Any {
//    return database.getPressureDataDao().getAllPressureData()
//}

}

fun playAudio() {
    val audioURL = "https://raw.githubusercontent.com/crdildy/WPMSProject/main/AlarmSound/beep-warning-6387.mp3"

    mediaPlayer = MediaPlayer()
    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
    try {
        mediaPlayer!!.setDataSource(audioURL)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()

    } catch (e : IOException) {
        e.printStackTrace()
    }

}
