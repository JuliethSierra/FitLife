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

    fun fetchExercisesByName(name: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _exercises.value = getExerciseByNameUseCase(name)
            } catch (e: Exception) {
                _exercises.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}