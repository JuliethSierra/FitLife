package com.example.fitlife.presentation.viewmodel

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

    fun fetchAllExercises() {
        viewModelScope.launch {
            try {
                _exercises.value = getAllExercisesUseCase()
            } catch (e: Exception) {
                _exercises.value = emptyList() // Manejo de errores
            }
        }
    }

    fun fetchExercisesByName(name: String) {
        viewModelScope.launch {
            try {
                _exercises.value = getExerciseByNameUseCase(name)
            } catch (e: Exception) {
                _exercises.value = emptyList() // Manejo de errores
            }
        }
    }
}
