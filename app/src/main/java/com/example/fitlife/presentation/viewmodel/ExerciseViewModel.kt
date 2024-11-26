package com.example.fitlife.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.usecase.GetAllExercisesUseCase
import com.example.fitlife.domain.usecase.GetExerciseByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        fetchAllExercises()
    }

    fun fetchAllExercises() {
        viewModelScope.launch {
            try {
                val exercisesList = getAllExercisesUseCase()
                _exercises.value = exercisesList
                Log.d("ExerciseViewModel", "Ejercicios cargados: ${exercisesList.size}")
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
                val exercise = getExerciseByNameUseCase(name).firstOrNull() // Ajusta según sea necesario
                _exerciseDetail.value = exercise
                Log.d("ExerciseViewModel", "Detalle cargado: $exercise")
            } catch (e: Exception) {
                _exerciseDetail.value = null
                Log.e("ExerciseViewModel", "Error al cargar detalle del ejercicio", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}