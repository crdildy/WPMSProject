package com.example.wpms
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.wpms.DAOs.PressureDataDao
import com.example.wpms.Entities.PressureData
import com.example.wpms.Model.PressureDB
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PressureDBTest {

    private lateinit var pressureDao: PressureDataDao
    private lateinit var db: PressureDB

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, PressureDB::class.java).build()
        pressureDao = db.pressureDataDao()
    }

        @After
        @Throws(IOException::class)
        fun closeDb() {
            db.close()
        }

        @Test
        @Throws(Exception::class)
        fun insertAndGetPressureData() = runBlocking {
            // Insert a test PressureData object
            val pressureData = PressureData(pressureValue = 50.0f, timestamp = System.currentTimeMillis())
            pressureDao.insert(pressureData)

            // Get the inserted PressureData from the database
            val retrievedData = pressureDao.getAllPressureData().first()

            // Verify that the retrieved data matches the inserted data
            assert(retrievedData == pressureData)
        }
    }