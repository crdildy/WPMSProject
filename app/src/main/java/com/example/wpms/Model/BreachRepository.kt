package com.example.wpms.Model


class BreachRepository(private val db: WpmsDB) {
    suspend fun insertBreach(breach: Breach) = db.getBreachDao().insertBreach(breach)
    suspend fun updateBreach(breach: Breach) = db.getBreachDao().updateBreach(breach)
    suspend fun deleteBreach(breach: Breach) = db.getBreachDao().deleteBreach(breach)

    fun getAllBreaches() = db.getBreachDao().getAllBreaches()

//    fun searchBreach(query: Int?) = db.getBreachDao().searchBreach(query)
}