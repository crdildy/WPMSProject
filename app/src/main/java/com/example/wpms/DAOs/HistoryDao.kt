package com.example.wpms.DAOs
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wpms.Entities.History
@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Update
    suspend fun updateHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("SELECT * FROM HISTORY ORDER BY id DESC")
    fun getAllHistory(): LiveData<List<History>>

    @Query("SELECT * FROM HISTORY WHERE time LIKE :query OR date LIKE :query")
    fun searchHistory(query: Int?): LiveData<List<History>>

}