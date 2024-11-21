package com.example.fitlife.data.remote.firebase.services

import android.util.Log
import com.example.fitlife.data.remote.firebase.FirebaseClient
import com.example.fitlife.presentation.ui.screens.utils.Constants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val firebaseClient: FirebaseClient) {

    suspend fun signUp(email: String, password: String): String? {
        return try {
            val result =
                firebaseClient.firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            return if(result.user != null){
                Log.d(Constants.TAG, "SignUp Successful ${result.user?.uid}")
                result.user?.uid
            }else{
                Log.d(Constants.TAG, "SignUp Failed")
                null
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "SignUp Error: ${e.message}")
            null
        }
    }

    suspend fun login(email: String, password: String): String? {
        return try {
            val result =
                firebaseClient.firebaseAuth.signInWithEmailAndPassword(email, password).await()

            return if(result.user != null){
                Log.d(Constants.TAG, "Login Successful ${result.user?.uid}")
                result.user?.uid
            }else{
                Log.d(Constants.TAG, "Login Failed")
                null
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Login Error: ${e.message}")
            null
        }
    }
}