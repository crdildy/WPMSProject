package com.example.wpms.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import com.example.wpms.database.PatientDatabase
import com.example.wpms.model.History
class HistoryRepository(private val db: PatientDatabase) {

    suspend fun insertHistory(history: History) = db.getHistoryDao().insertHistory(history)

    suspend fun updateBranch(history: History) = db.getHistoryDao().updateHistory(history)
    suspend fun deleteBranch(history: History) = db.getHistoryDao(). deleteHistory(history)
    fun getAllHistory() = db.getHistoryDao().getAllHistory()
    fun searchHistory(query: Int?) = db.getHistoryDao().searchHistory(query)
}