package com.example.wpms.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wpms.Model.FirebaseRepository
import java.sql.Timestamp

class PatientHomeActivityViewModel(private val firebaseRepository: FirebaseRepository): ViewModel() {
    private val pressureData = MutableLiveData<List<Int>>()
//    val pressureData: LiveData<List<Int>> get() = pressureData
    fun insertPressureData(deviceID: String, pressure_center: Float, pressure_left: Float, pressure_right: Float, timestamp: Timestamp) {
        // Insert pressure data into Firestore
        firebaseRepository.insertPressureData(deviceID, pressure_center, pressure_left, pressure_right, timestamp)
    }
}

class ViewModelFactory(private val firebaseRepository: FirebaseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientHomeActivityViewModel::class.java)) {
            return PatientHomeActivityViewModel(firebaseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}