package com.example.wpms.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val patientsCollection = db.collection("patients")
    private val pressureCollection = db.collection("pressure_data")
    //add other collections here


    //Patient collection methods
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

    //Pressure collection methods
    fun insertPressureData(deviceID: String, pressure_center: Int, pressure_left: Int, pressure_right: Int, timestamp: Timestamp){
        val pressureDocRef = pressureCollection.document(deviceID)
        val pressureData = hashMapOf(
            "deviceID" to deviceID,
            "pressure_center" to pressure_center,
            "pressure_left" to pressure_left,
            "pressure_right" to pressure_right,
            "timestamp" to timestamp
        )
        pressureDocRef.set(pressureData)
            .addOnSuccessListener {
                println("Pressure document created/updated in Firestore for deviceID: $deviceID")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating pressure document in Firestore: $e")
            }
    }
    //to view all pressure docs, given deviceID
    fun getAllPressure(){

    }
    //Use for grabbing recent data, given device ID (can be used for graph?)
    fun getRecentPressure(){

    }
}



