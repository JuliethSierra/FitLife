package com.example.fitlife.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    suspend fun signUp(user: User): Boolean = signUpUseCase.invoke(user)
    suspend fun addImage(selectedImageUri: Uri):String {
        return signUpUseCase.addImage(selectedImageUri)
    }
}