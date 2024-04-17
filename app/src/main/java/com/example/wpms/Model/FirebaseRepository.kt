package com.example.wpms.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val patientsCollection = db.collection("users")
    private val pressureCollection = db.collection("pressure_data")
    private val moistureCollection = db.collection("moisture_data")
    //add other collections here

    fun insertMoistureData(userId: String, isMoist: Int, timestamp: Timestamp){
        //initializes a variable to reference a document in the 'moisture_data' collection identified by 'userId'
        val moistureDataDocRef = moistureCollection.document(userId)



        //initializes a HashMap, 'moistureData', that maps the 'userId', 'isMoist', and 'timestamp' keys
        //to the values of the corresponding passed parameters of the function
        val moistureData = hashMapOf(
            "userId" to userId,
            "isMoist" to isMoist,
            "timestamp" to timestamp
        )

        moistureDataDocRef.set(moistureData)
            .addOnSuccessListener {
                println("MoistureData document created/updated in Firestore for user: $userId")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating moistureDataq document in Firestore: $e")
            }

    }

    //Patient collection methods
    fun addUser(userId: String, name: String, roomNumber: String, role: String) {
        val patientDocRef = patientsCollection.document(userId)

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

    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun getUserRole(userId: String?, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
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
                    onFailure.invoke()
                }
            } else {
                onFailure.invoke()
                Log.d("FirebaseRepository", "document does not exist")
            }
        }?.addOnFailureListener {
            onFailure.invoke()
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



