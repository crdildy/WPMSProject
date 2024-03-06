package com.example.wpms.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PressureDataViewModel(private val repository: PressureRepository) : ViewModel() {
    fun insertPressureData(pressureData: Pressure){
        viewModelScope.launch {
            repository.insertPressureDao(pressureData)
        }
    }

    suspend fun getAllPressureData() = repository.getAllPressureData()
}


class PressureDataViewModelFactory(private val repository: PressureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PressureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PressureDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}