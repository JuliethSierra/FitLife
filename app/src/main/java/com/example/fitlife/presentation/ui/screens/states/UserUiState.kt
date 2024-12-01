package com.example.fitlife.presentation.ui.screens.states

import com.example.fitlife.domain.model.User

data class UserUiState(
    val usersList: List<User>? = emptyList(),
    val completedExercises: List<String> = emptyList(),
    val user: User? = null,
    val isLoading: Boolean = true
)