package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.usecase.GetAllExercisesUseCase
import com.example.fitlife.domain.usecase.GetExerciseByNameUseCase
import com.example.fitlife.presentation.ui.screens.states.ExerciseUIState
import com.example.fitlife.presentation.ui.screens.states.LogInUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val getExerciseByNameUseCase: GetExerciseByNameUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(ExerciseUIState())
    val uiState: StateFlow<ExerciseUIState> = _uiState.asStateFlow()

    init {
        fetchAllExercises()
    }

    private fun fetchAllExercises() {
        viewModelScope.launch {
            try {
                val response = getAllExercisesUseCase.invoke()
                Log.d("AndroidRuntime", response.toString())

                _uiState.value = _uiState.value.copy(
                    exerciseList = response,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
                Log.e("ExerciseViewModel", "Error al cargar ejercicios", e)
            }
        }
    }

    // Cargar un ejercicio específico por nombre
    fun fetchExerciseByName(name: String) {
        viewModelScope.launch {
            try {
                val exercises = getExerciseByNameUseCase.invoke(name)
                val exercise = exercises.first()
                _uiState.value = _uiState.value.copy(
                    exercise = exercise,
                    isLoading = false
                )
                Log.d("ExerciseViewModel", "Detalle cargado: ${_uiState.value.exercise}")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    exercise = null,
                    isLoading = false
                )
                Log.e("ExerciseViewModel", "Error al cargar detalle del ejercicio", e)
            }
        }
    }
}