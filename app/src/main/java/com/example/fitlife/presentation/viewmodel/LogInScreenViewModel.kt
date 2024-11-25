package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.usecases.IsLoggedUseCase
import com.example.fitlife.domain.model.usecases.LoginUseCase
import com.example.fitlife.domain.model.usecases.SignOutUseCase
import com.example.fitlife.presentation.ui.screens.states.LogInUIState
import com.example.fitlife.presentation.ui.screens.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val isLoggedUseCase: IsLoggedUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogInUIState())
    val uiState: StateFlow<LogInUIState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.d(Constants.TAG, "Start to login into VM")
            _uiState.value = _uiState.value.copy(
                userUiState = loginUseCase.invoke(email, password),
            )
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }

    fun isLogged() {
        viewModelScope.launch {
            Log.d("AndroidRuntime", "Starting is logged")

            val response = isLoggedUseCase.invoke()

            Log.d("AndroidRuntime", "Response vm: $response")

            _uiState.value = _uiState.value.copy(
                isFinished = true,
                isLogged = response
            )
        }
    }
}
