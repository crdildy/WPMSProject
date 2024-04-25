package com.example.wpms.Model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    private val pressureCollection = db.collection("pressure_data")
    private val moistureCollection = db.collection("moisture_data")
    private val breachCollection = db.collection("breach_data")
    private val patientCollection = db.collection("patients")
    private val caregiverCollection = db.collection("caregivers")

    fun addCaregiver(userId: String, name: String, patients: Array<String>) {
        val caregiverDataDocRef = caregiverCollection.document(userId)
        val caregiver = hashMapOf(
            "userId" to userId,
            "name" to name,
            "patients" to patients.toList()
        )

        caregiverDataDocRef.set(caregiver)
            .addOnSuccessListener {
                println("Caregiver document successfully created/updated in Firestore for $userId")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating caregiver document in Firestore: $e")
            }
    }
    fun insertMoistureData(userId: String, isMoist: Int, timestamp: Timestamp){
        // Generate a unique document ID
        val documentId = "$userId-${timestamp.time}"


        //initializes a HashMap, 'moistureData', that maps the 'userId', 'isMoist', and 'timestamp' keys
        //to the values of the corresponding passed parameters of the function
        val moistureData = hashMapOf(
            "userId" to userId,
            "isMoist" to isMoist,
            "timestamp" to timestamp
        )

        // Use the generated document ID for the new document
        moistureCollection.document(documentId).set(moistureData)
        // Insert data as a new document in the pressure_data collection
        moistureCollection.document(documentId).set(moistureData)
            .addOnSuccessListener {
                println("New moisture data document created/updated in Firestore with ID: $documentId")
                println("New moisture document created/updated in Firestore with ID: $documentId")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating moisture document in Firestore: $e")
            }

    }

    fun addPatientToList(caregiverId: String, patientId: String) {
        val caregiverDocRef = caregiverCollection.document(caregiverId)

        caregiverDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val patients = document.get("patients") as? List<String>
                    if (patients != null) {
                        val updatedPatients = patients.toMutableList()
                        updatedPatients.add(patientId)
                        caregiverDocRef.update("patients", updatedPatients)
                            .addOnSuccessListener {
                                println("Patient added to caregiver's list")
                            }
                            .addOnFailureListener { e ->
                                println("Error adding patient to caregiver's list: $e")
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Error creating/updating moisture data document in Firestore: $e")
            }

    }

    //Patient collection methods
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

    fun addPatient(userId: String, name: String, roomNumber: String, caregivers: Array<String>) {
        val patientDocRef = patientCollection.document(userId)

        val patientData = hashMapOf(
            "userId" to userId,
            "name" to name,
            "roomNumber" to roomNumber,
            "caregivers" to caregivers.toList()
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

    fun getCaregiverPatients(caregiverId: String, onSuccess: (List<User>) -> Unit, onFailure: (Exception) -> Unit) {
    val caregiverDocRef = caregiverCollection.document(caregiverId)

    caregiverDocRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val patientIds = document.get("patients") as? List<String>
                if (patientIds != null) {
                    val patients = mutableListOf<User>()
                    val patientCount = patientIds.size
                    var fetchedCount = 0

                    for (patientId in patientIds) {
                        usersCollection.document(patientId).get()
                            .addOnSuccessListener { patientDocument ->
                                val patient = patientDocument.toObject(User::class.java)
                                if (patient != null) {
                                    patients.add(patient)
                                }
                                fetchedCount++
                                if (fetchedCount == patientCount) {
                                    onSuccess(patients)
                                }
                            }
                            .addOnFailureListener { exception ->
                                onFailure(exception)
                            }
                    }
                } else {
                    onFailure(Exception("No patients found for caregiver"))
                }
            } else {
                onFailure(Exception("Caregiver document does not exist"))
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
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

    fun insertBreach(userId: String, isMoistDetected: Boolean, isPressureDetected: Boolean, timestamp: Timestamp){
        // Generate a unique document ID
        val documentId = "$userId-${timestamp.time}"


        //initializes a HashMap, 'moistureData', that maps the 'userId', 'isMoist', and 'timestamp' keys
        //to the values of the corresponding passed parameters of the function
        val breachData = hashMapOf(
            "userId" to userId,
            "isMoistDetected" to isMoistDetected,
            "isPressureDetected" to isPressureDetected,
            "timestamp" to timestamp
        )

        // Use the generated document ID for the new document
        breachCollection.document(documentId).set(breachData)
            .addOnSuccessListener {
                println("New breach data document created/updated in Firestore with ID: $documentId")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating breach data document in Firestore: $e")
            }

    }

    //Pressure collection methods
    fun insertPressureData(deviceID: String, pressure_center: Float, pressure_left: Float, pressure_right: Float, timestamp: Timestamp) {
        // Generate a document ID that combines deviceID and timestamp for uniqueness
        val documentId = "$deviceID-${timestamp.time}"

        val pressureData = hashMapOf(
            "deviceID" to deviceID,
            "pressure_center" to pressure_center,
            "pressure_left" to pressure_left,
            "pressure_right" to pressure_right,
            "timestamp" to timestamp
        )

        // Insert data as a new document in the pressure_data collection
        pressureCollection.document(documentId).set(pressureData)
            .addOnSuccessListener {
                println("New pressure document created/updated in Firestore with ID: $documentId")
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
