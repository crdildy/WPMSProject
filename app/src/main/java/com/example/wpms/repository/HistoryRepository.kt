package com.example.wpms.repository

import com.example.wpms.DAOs.PatientDatabase
import com.example.wpms.Entities.History
class HistoryRepository(private val db: PatientDatabase) {

    suspend fun insertHistory(history: History) = db.getHistoryDao().insertHistory(history)

    suspend fun updateBranch(history: History) = db.getHistoryDao().updateHistory(history)
    suspend fun deleteBranch(history: History) = db.getHistoryDao(). deleteHistory(history)
    fun getAllHistory() = db.getHistoryDao().getAllHistory()
    fun searchHistory(query: Int?) = db.getHistoryDao().searchHistory(query)
}