package com.example.fitlife.presentation.ui.screens.states

import com.example.fitlife.domain.model.Exercise

data class ExerciseUIState(
    val exerciseList: List<Exercise>? = emptyList(),
    val exercise: Exercise? = null,
    val isLoading: Boolean = true
)
