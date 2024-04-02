package com.example.wpms.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wpms.Entities.Patient
import com.example.wpms.repository.PatientRepository
import kotlinx.coroutines.launch

class CaregiverViewModel(private val repository: PatientRepository) : ViewModel() {


        val allPatients: LiveData<List<Patient>> = repository.allPatients.asLiveData()

        fun insert(patient: Patient) = viewModelScope.launch{
            repository.insert(patient)
        }

        fun getAllPatients(patient: Patient) = viewModelScope.launch{
            viewModelScope.launch{}
            repository.getAllPatients()
        }

    }

    class CaregiverViewModelFactory(private val repository: PatientRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CaregiverViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CaregiverViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
