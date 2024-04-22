package com.example.wpms.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    fun addUser(userId: String, name: String, roomNumber: String, role: String) {
        val patientDocRef = usersCollection.document(userId)

        val patientData = hashMapOf(
            "userId" to userId,
            "name" to name,
            "roomNumber" to roomNumber,
            "role" to role
        )

        patientDocRef.set(patientData)
            .addOnSuccessListener {
                println("Patient document created/updated in Firestore for user: $userId")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating patient document in Firestore: $e")
            }
    }

    fun getUserRole(userId: String?, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        Log.d("RepositoryData", "getUserRole function called")
        val userDoc = userId?.let { db.collection("users").document(it) }
        Log.d("RepositoryData", "User Document: $userDoc")
        Log.d("RepositoryData", "User Document: $userId")

        userDoc?.get()?.addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val userRole = documentSnapshot.getString("role")
                Log.d("RepositoryData", "fetching userRole: $userRole")
                if (userRole != null) {
                    onSuccess.invoke(userRole)
                } else {
                    onFailure.invoke(Exception("Role field is null"))
                }
            } else {
                onFailure.invoke(Exception("User document does not exist"))
                Log.d("RepositoryData", "document does not exist")
            }
        }?.addOnFailureListener { e ->
            Log.e("RepositoryData", "Error fetching user role: ${e.message}")
            onFailure.invoke(e)
        }
    }

    fun getPatients(onSuccess: (List<User>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("patients")
            .get()
            .addOnSuccessListener { result ->
                val patients = result.map { it.toObject(User::class.java) }
                onSuccess(patients)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}



