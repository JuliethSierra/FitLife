package com.example.fitlife.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.usecase.GetExerciseUseCase
import kotlinx.coroutines.launch

class ExerciseViewModel(private val getExerciseUseCase: GetExerciseUseCase) : ViewModel() {
    private val _exercise = MutableLiveData<List<Exercise>>()
    val exercise: LiveData<List<Exercise>> get() = _exercise

    fun fetchExercise(name: String) {
        viewModelScope.launch {
            try {
                val result = getExerciseUseCase(name)
                _exercise.value = result
            } catch (e: Exception) {
                _exercise.value = emptyList() // Manejo de errores
            }
        }
    }
}