package com.example.fitlife.domain.model.usecases

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.fitlife.data.remote.firebase.services.AuthenticationService
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.domain.model.User
import com.example.fitlife.presentation.ui.screens.utils.Constants
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend fun invoke(user: User): Boolean {
        val uid: String? = authenticationService.signUp(user.email, user.password)
        return if (uid != null) {
            user.uid = uid
            userService.saveData(user)
        } else {
            false
        }
    }

    suspend fun addImage(selectedImageUri: Uri): String {
        Log.d(Constants.TAG,selectedImageUri.toString())
        return userService.addImage(selectedImageUri)
    }
}