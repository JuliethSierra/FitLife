package com.example.fitlife.presentation.ui.screens.states

import com.example.fitlife.data.models.User

data class UserUiState(
    val usersList: List<User>? = emptyList(),
    val isLoading: Boolean = true
)