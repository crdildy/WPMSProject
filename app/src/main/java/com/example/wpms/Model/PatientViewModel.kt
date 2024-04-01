package com.example.wpms.Model


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wpms.Entities.PressureData
import com.example.wpms.repository.PressureDataRepo
import kotlinx.coroutines.launch


class PatientViewModel(private val repository: PressureDataRepo) : ViewModel() {

    //private val allPressure = repository.getAllPressureData()
    val allPressure: LiveData<List<PressureData>> = repository.allPressure.asLiveData()

    fun insert(pressureData: PressureData) = viewModelScope.launch{
        repository.insert(pressureData)
    }

    fun getAllPressureData(pressureData: PressureData) = viewModelScope.launch{
        viewModelScope.launch{}
        repository.getAllPressureData()
    }

}

class PatientViewModelFactory(private val repository: PressureDataRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}