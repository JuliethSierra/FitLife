package com.example.fitlife.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.usecases.SignUpUseCase
import com.example.fitlife.presentation.ui.screens.states.SingUpUIState
import com.example.fitlife.presentation.ui.screens.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SingUpUIState())
    val uiState: StateFlow<SingUpUIState> = _uiState.asStateFlow()

    fun signUp(user: User) {
        viewModelScope.launch {
            Log.d(Constants.TAG, "Start to signUp into VM")

            _uiState.value = _uiState.value.copy(
                isSuccessful = signUpUseCase.invoke(user)
            )
        }
    }

    suspend fun addImage(selectedImageUri: Uri): String {
        return signUpUseCase.addImage(selectedImageUri)
    }
}