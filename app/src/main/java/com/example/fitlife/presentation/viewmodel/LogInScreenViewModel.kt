package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.usecases.LoginUseCase
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
        private val loginUseCase: LoginUseCase
    ) : ViewModel() {

        private val _uiState = MutableStateFlow(LogInUIState())
        val uiState: StateFlow<LogInUIState> = _uiState.asStateFlow()

        fun login(email: String, password: String) {
            viewModelScope.launch {
                try {
                    Log.d(Constants.TAG, "Start to login into VM")
                    _uiState.value = _uiState.value.copy(
                        uid = loginUseCase.invoke(email, password),
                        successLogIn = true
                    )

                } catch (e: Exception) {
                    Log.d(Constants.TAG, "Start error to login into VM")
                    _uiState.value = _uiState.value.copy(
                        uid = loginUseCase.invoke(email, password),
                        successLogIn = false
                    )
                }
            }
        }
    }
/*fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
    viewModelScope.launch {
        try {
            val result = loginUseCase.invoke(email, password)
            // Si el login es exitoso
            onResult(true, null)
        } catch (e: Exception) {
            // Si ocurre un error durante el login
            onResult(false, e.message)
        }
    }*/

