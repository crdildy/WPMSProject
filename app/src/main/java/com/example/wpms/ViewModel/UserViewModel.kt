package com.example.wpms.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wpms.Model.FirebaseRepository
class UserViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {

    fun getUserRole(userId: String?, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        firebaseRepository.getUserRole(userId, onSuccess, onFailure)
        Log.d("vmGetUser", "fetching role of userId: $userId")
    }
}