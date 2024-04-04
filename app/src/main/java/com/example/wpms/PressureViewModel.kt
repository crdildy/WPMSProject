package com.example.wpms


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class PressureViewModel(private val repository: PressureRepository) : ViewModel() {

    //private val allPressure = repository.getAllPressureData()
    val pressureData: LiveData<List<PressureData>> = repository.allPressureData.asLiveData()

    fun insert(pressureData: PressureData) = viewModelScope.launch{
        repository.insertPressureData(pressureData)
    }

//    fun getAllPressureData(pressureData: PressureData) = viewModelScope.launch{
//        viewModelScope.launch{}
//        repository.getAllPressureData()
//    }

}

class PressureViewModelFactory(private val repository: PressureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PressureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PressureViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}