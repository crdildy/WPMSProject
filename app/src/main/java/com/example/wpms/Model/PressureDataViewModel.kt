package com.example.wpms.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wpms.Entities.PressureData
import com.example.wpms.repository.PressureDataRepo
import kotlinx.coroutines.launch

class PressureDataViewModel(private val repository: PressureDataRepo) : ViewModel() {

    //val allPressure: LiveData<List<PressureData>> = repository.getAllPressureData()

    fun insert(pressureData: PressureData) = viewModelScope.launch{
            repository.insert(pressureData)
        }
    }

//    fun getAllPressureData(pressureData: PressureData) = viewModelScope.launch{
//        viewModelScope.launch{}
//        repository.getAllPressureData()
//    }
    //suspend fun getAllPressureData() = repository.getAllPressureData()
//}

//factory gets the dependencies to build out pressureDataViewModel
class PressureDataViewModelFactory(private val repository: PressureDataRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PressureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PressureDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}