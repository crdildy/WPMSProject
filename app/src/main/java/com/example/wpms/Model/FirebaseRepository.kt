package com.example.wpms.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val patientsCollection = db.collection("patients")

    fun addPatient(userId: String, name: String, roomNumber: String) {
        val patientDocRef = patientsCollection.document(userId)

        val patientData = hashMapOf(
            "userId" to userId,
            "name" to name,
            "roomNumber" to roomNumber
        )

        patientDocRef.set(patientData)
            .addOnSuccessListener {
                println("Patient document created/updated in Firestore for user: $userId")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating patient document in Firestore: $e")
            }
    }

    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun getPatient(
        onSuccess: (List<Patient>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        patientsCollection.get()
            .addOnSuccessListener { result ->
                val patientList = mutableListOf<Patient>()
                for (document in result) {
                    val userId = document.id
                    val name = document.getString("name") ?: ""
                    val roomNumber = document.getString("roomNumber") ?: ""
                    patientList.add(Patient(userId, name, roomNumber))
                }
                onSuccess(patientList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}



