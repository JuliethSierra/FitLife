package com.example.fitlife.presentation.ui.screens.states

import com.example.fitlife.domain.model.User

data class LogInUIState(
    val userUiState: User? = null,
    val isLogged: Boolean = false,
    val isFinished: Boolean = false
)
