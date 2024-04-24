package com.example.wpms.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wpms.Entities.PressureData
import com.example.wpms.repository.PressureDataRepo
import kotlinx.coroutines.launch

class PressureDataViewModel(private val repository: PressureDataRepo) : ViewModel() {
    fun insertPressureData(pressureData: PressureData){
        viewModelScope.launch {
            repository.insertPressureDao(pressureData)
        }
    }

    suspend fun getAllPressureData() = repository.getAllPressureData()
}


class PressureDataViewModelFactory(private val repository: PressureDataRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PressureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PressureDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}