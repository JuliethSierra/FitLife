package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.data.repository.AuthRepositoryImpl
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.usecases.AddCompleteExerciseUseCase
import com.example.fitlife.domain.model.usecases.GetAllCompleteExerciseUseCase
import com.example.fitlife.domain.usecase.GetAllUsersCommunityUseCase
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
    private val addCompleteExerciseUseCase: AddCompleteExerciseUseCase,
    private val getAllCompleteExerciseUseCase: GetAllCompleteExerciseUseCase,
    private val getAllUsersCommunityUseCase: GetAllUsersCommunityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUsers() {
        viewModelScope.launch {
        }
    }

    fun addCompletedExercise(exerciseName: String) {
        viewModelScope.launch {
            val success = addCompleteExerciseUseCase.invoke(exerciseName)

            Log.e("FitLife", "CompletedExercisesSuccessful $success")

            if (success) {
                _uiState.update { state ->
                    Log.e("FitLife", "CompletedExercises ${state.completedExercises}")
                    state.copy(
                        completedExercises = state.completedExercises
                    )
                }
            } else {
                Log.e("FitLife", "Error al agregar ejercicio completado")
            }
        }
    }

    fun loadCompletedExercises() {
        viewModelScope.launch {
            val exercises = getAllCompleteExerciseUseCase.invoke()
            _uiState.update { state ->
                state.copy(completedExercises = exercises)
            }

            Log.e("FitLife", "CompletedExercises: $exercises")
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                val success = repository.updateUser(user) // Llama al repositorio para manejar la actualización

                if (success) {
                    Log.d("UserViewModel", "Usuario actualizado correctamente en Firebase")
                    _uiState.update { state ->
                        state.copy(user = user) // Actualiza el estado con el nuevo usuario
                    }
                } else {
                    Log.e("UserViewModel", "No se pudo actualizar el usuario")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error al actualizar usuario: ${e.message}")
            }
        }
    }


    fun loadAllUsersCommunity(){
        viewModelScope.launch {
            val usersCommunity = getAllUsersCommunityUseCase.invoke()
            _uiState.update { state ->
                state.copy(usersList = usersCommunity)
            }

            Log.d("FitLife", "Community: $usersCommunity")
        }
    }

}
