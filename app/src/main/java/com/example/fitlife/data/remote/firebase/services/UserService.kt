package com.example.fitlife.data.remote.firebase.services

import android.net.Uri
import android.util.Log
import com.example.fitlife.data.remote.firebase.FirebaseClient
import com.example.fitlife.data.remote.firebase.mappers.toUser
import com.example.fitlife.domain.model.User
import com.example.fitlife.presentation.ui.screens.utils.Constants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService @Inject constructor(private val firebaseClient: FirebaseClient) {

    private var downloadUrl: String = ""

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

        val response = firebaseClient.firestore.collection(Constants.COLLECTION_USERS).add(data).await()

        Log.d(Constants.TAG, "The response is: ${response != null}")

        return response != null
    }


    suspend fun addImage(selectedImageUri: Uri):String {
        val storage = firebaseClient.store.reference
        val reference = storage.child("${System.currentTimeMillis()}.jpg")
        val uploadTask = reference.putFile(selectedImageUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            reference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                // Convierte la URL a una cadena
                downloadUrl = downloadUri.toString()
                Log.d(Constants.TAG, "Register Successful image")
            } else {
                Log.d(Constants.TAG, "Register NOT Successful")
            }
        }.await()

        return downloadUrl
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

}
