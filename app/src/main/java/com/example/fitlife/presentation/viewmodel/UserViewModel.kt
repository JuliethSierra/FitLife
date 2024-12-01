package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.data.repository.AuthRepositoryImpl
import com.example.fitlife.presentation.ui.screens.states.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
    private val userService: UserService
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUsers() {
        viewModelScope.launch {
        }
    }

    fun addCompletedExercise(exerciseName: String) {
        val uid = _uiState.value.user?.uid ?: return
        viewModelScope.launch {
            val success = userService.addCompletedExercise(uid, exerciseName)
            if (success) {
                _uiState.update { state ->
                    state.copy(
                        completedExercises = state.completedExercises + exerciseName
                    )
                }
            }
        }
    }


    fun loadCompletedExercises() {
        val uid = _uiState.value.user?.uid ?: return
        viewModelScope.launch {
            val exercises = userService.getCompletedExercises(uid)
            _uiState.update { state ->
                state.copy(completedExercises = exercises)
            }
        }
    }

}

