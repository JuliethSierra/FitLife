package com.example.fitlife.data.remote.firebase.services

import android.net.Uri
import android.util.Log
import com.example.fitlife.data.remote.firebase.FirebaseClient
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.example.fitlife.presentation.ui.screens.utils.Constants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.util.TreeMap
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



 /*   suspend fun getUserByUid(uid: String): UserSP? {
        val user =
            firebaseClient.firestore.collection(Constants.COLLECTION_USERS).whereEqualTo("uid", uid)
                .get().await().documents[0]

        var path = user.reference.path
        var role = Util.adaptRole(user.getString("role"))

        return role?.let { UserSP(path, it, uid) }
    }*/

}
