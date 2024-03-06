import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.wpms.Model.WpmsDB
import com.example.wpms.Model.Pressure
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class PressureDaoTest {

    private lateinit var wpmsDB: WpmsDB

//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        wpmsDB = Room.inMemoryDatabaseBuilder(context, WpmsDB::class.java).build()
//    }
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        wpmsDB = Room.inMemoryDatabaseBuilder(context, WpmsDB::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        wpmsDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadPressure() = runBlocking {
//        val pressureValue = "120" // Change this to the value you expect to insert
        val randomPressureValue = Random.nextInt(0, 200).toString()
        // Insert pressure data into the database
        wpmsDB.getPressureDao().insert(Pressure(randomPressureValue))

        // Read pressure data from the database
        val pressureFromDb = wpmsDB.getPressureDao().getAllPressureData()
        assert(pressureFromDb.isNotEmpty())
        val pressureExists = pressureFromDb.any{ it.pressureValue == randomPressureValue}
        assert(pressureExists)
        // Verify that pressure data was inserted correctly
//        assert(pressureFromDb != null)
//        assert(pressureFromDb!!.Pressure == randomPressureValue)
    }
}
