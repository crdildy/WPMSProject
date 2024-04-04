package com.example.wpms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PressureDataViewModel(private val repository: PressureRepository) : ViewModel() {

    //val allPressure: LiveData<List<PressureData>> = repository.getAllPressureData()

    fun insert(pressureData: PressureData) = viewModelScope.launch{
            repository.insertPressureData(pressureData)
        }
    }

//    fun getAllPressureData(pressureData: PressureData) = viewModelScope.launch{
//        viewModelScope.launch{}
//        repository.getAllPressureData()
//    }
    //suspend fun getAllPressureData() = repository.getAllPressureData()
//}

//factory gets the dependencies to build out pressureDataViewModel
class PressureDataViewModelFactory(private val repository: PressureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PressureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PressureDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}