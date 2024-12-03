package com.example.fitlife.data.remote.firebase.services

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.fitlife.data.remote.firebase.FirebaseClient
import com.example.fitlife.data.remote.firebase.mappers.toUser
import com.example.fitlife.domain.model.User
import com.example.fitlife.presentation.ui.screens.utils.Constants
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService @Inject constructor(private val firebaseClient: FirebaseClient) {

    // Método para guardar los datos del usuario en Firestore
    suspend fun saveData(user: User): Boolean {
        val data = hashMapOf(
            "name" to user.name,
            "lastName" to user.lastName,
            "email" to user.email,
            "height" to user.height,
            "birthDate" to user.birthDate,
            "gender" to user.gender.name,
            "weight" to user.weight,
            "profilePictureUrl" to user.profilePictureUrl,
            "numberPhone" to user.numberPhone,
            "uid" to user.uid
        )

        val response =
            firebaseClient.firestore.collection(Constants.COLLECTION_USERS).add(data).await()

        Log.d(Constants.TAG, "The response is: ${response != null}")

        return response != null
    }

    // Método para agregar la imagen al almacenamiento de Firebase y devolver la URL
    suspend fun addImage(selectedImageUri: Uri): String {
        // Obtén la referencia al almacenamiento de Firebase
        Log.d("UserService", "Adding $selectedImageUri")

        // Genera un nombre único para el archivo usando el tiempo actual
        val reference = firebaseClient.storage.reference.child("profile_pictures/${System.currentTimeMillis()}.jpg")

        Log.d("UserService", "Reference $reference")

        var file = Uri.fromFile(File(selectedImageUri.toString()))
        //val stream = FileInputStream(File("${selectedImageUri.toString().replace("content://","")}.jpg"))

        // Carga el archivo
        val uploadTask = reference.putFile(file)

        Log.d("UserService", "Upload task $uploadTask")

        // Espera hasta que la carga se complete y obtén la URL de descarga
        return try {
            // Espera a que la carga termine con continueWithTask
            val task = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                // Retorna la URL de descarga después de la carga
                reference.downloadUrl
            }.await()

            // Devuelve la URL de descarga como String
            task.toString()  // Convertir la URL a String
        } catch (e: Exception) {
            // Si hay algún error, manejarlo aquí
            Log.e("Firebase", "Error uploading image", e)
            ""
        }
    }

    suspend fun getUserByUid(uid: String): User? {
        return try {
            val response = firebaseClient.firestore
                .collection(Constants.COLLECTION_USERS)
                .whereEqualTo("uid", uid)
                .get()
                .await()

            if (!response.isEmpty) {
                val document = response.documents[0]
                document.toUser()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error getting user by UID: ${e.message}", e)
            null
        }
    }

    suspend fun addCompletedExercise(uid: String, exerciseName: String): Boolean {
        Log.d("FitLife", "UID: $uid, Exercise: $exerciseName")
        return try {
            Log.d("FitLife", "Updating Firestore with exercise: $exerciseName for UID: $uid")

            val data = hashMapOf("name" to exerciseName)

            val response = firebaseClient.firestore.collection(Constants.COLLECTION_USERS)
                .whereEqualTo("uid", uid).get().await()

            for (i in response.documents) {
                i.reference.collection(Constants.COLLECTION_DONE_EXERCISES).add(data).await()
            }

            Log.d("FitLife", "Firestore update successful")
            true
        } catch (e: Exception) {
            Log.e("FitLife", "Error adding completed exercise: ${e.message}", e)
            false
        }
    }

    suspend fun getCompletedExercises(uid: String): List<String> {
        return try {
            Log.d("FitLife", "getCompletedExercises")

            var completedExercises: MutableList<String> = mutableListOf()

            val response = firebaseClient.firestore
                .collection(Constants.COLLECTION_USERS)
                .whereEqualTo("uid", uid).get().await()

            Log.d("FitLife", "response $response")

            for (i in response.documents) {
                val doneExercisesSnapshot = i.reference
                    .collection(Constants.COLLECTION_DONE_EXERCISES)
                    .get()
                    .await()

                Log.d("FitLife", "documents $i")

                for (exerciseDoc in doneExercisesSnapshot.documents) {
                    Log.d("FitLife", "Data document ${exerciseDoc.data}")

                    exerciseDoc.data?.let {
                        completedExercises.add(
                            exerciseDoc.getString("name") ?: ""
                        )
                    }
                }
            }

            completedExercises
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error getting completed exercises: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try {
            // Encuentra el documento en Firebase usando el UID
            val querySnapshot = firebaseClient.firestore
                .collection(Constants.COLLECTION_USERS)
                .whereEqualTo("uid", user.uid)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]

                // Actualiza el documento con los nuevos datos
                document.reference.update(
                    mapOf(
                        "name" to user.name,
                        "lastName" to user.lastName,
                        "email" to user.email,
                        "height" to user.height,
                        "birthDate" to user.birthDate,
                        "gender" to user.gender.name,
                        "weight" to user.weight,
                        "profilePictureUrl" to user.profilePictureUrl,
                        "numberPhone" to user.numberPhone
                    )
                ).await()
                true
            } else {
                false // Si no se encuentra el usuario
            }
        } catch (e: Exception) {
            Log.e("UserService", "Error al actualizar usuario: ${e.message}")
            false
        }
    }

    suspend fun getAllDocuments(): List<User> {
        val documentsList = mutableListOf<User>()

        try {
            val snapshot = firebaseClient.firestore
                .collection(Constants.COLLECTION_USERS)
                .get()
                .await()

            for (document in snapshot.documents) {
                if (document.data != null) {
                    var currentUser = document.toUser()
                    val response =
                        document.reference.collection(Constants.COLLECTION_DONE_EXERCISES).get()
                            .await()
                    if (!response.isEmpty) {
                        var listDoneExercises = mutableListOf<String>()
                        for (i in response.documents) {
                            listDoneExercises.add(
                                i.getString(
                                    "name" +
                                            ""
                                )!!
                            )
                        }
                        currentUser.completedExercises = listDoneExercises
                        documentsList.add(currentUser)
                    }
                }
            }
            Log.d("FitLife", "Data community $documentsList")
        } catch (e: Exception) {
            println("Error al obtener los documentos: ${e.message}")
        }

        return documentsList

    }

}
