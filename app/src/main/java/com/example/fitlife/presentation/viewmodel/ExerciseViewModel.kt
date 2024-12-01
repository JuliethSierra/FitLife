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

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> get() = _exercises

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _exerciseDetail = MutableLiveData<Exercise?>()
    val exerciseDetail: LiveData<Exercise?> get() = _exerciseDetail

    private val _uiState = MutableStateFlow(ExerciseUIState())
    val uiState: StateFlow<ExerciseUIState> = _uiState.asStateFlow()

    init {
        fetchAllExercises()
    }

    private fun fetchAllExercises() {
        viewModelScope.launch {
            Log.d("AndroidRuntime", getAllExercisesUseCase.invoke().toString())
            try {
                _uiState.value = _uiState.value.copy(
                    exerciseList = getAllExercisesUseCase.invoke(),
                    isLoading = false
                )
                //val exercisesList = getAllExercisesUseCase()
               // _exercises.value = exercisesList
                //Log.d("ExerciseViewModel", "Ejercicios cargados: ${exercisesList.size}")
            } catch (e: Exception) {
                _exercises.value = emptyList()
                Log.e("ExerciseViewModel", "Error al cargar ejercicios", e)
            }
        }
    }

    // Cargar un ejercicio específico por nombre
    fun fetchExerciseByName(name: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    exercise = getExerciseByNameUseCase(name).firstOrNull(),
                    isLoading = false
                )
                Log.d("ExerciseViewModel", "Detalle cargado: ${_uiState.value.exercise}")
            } catch (e: Exception) {
                _exerciseDetail.value = null
                Log.e("ExerciseViewModel", "Error al cargar detalle del ejercicio", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}