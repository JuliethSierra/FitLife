package com.example.fitlife.presentation.ui.screens.training

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitlife.presentation.viewmodel.ExerciseViewModel

@Composable
fun TrainingScreenWithViewModel() {
    val viewModel: ExerciseViewModel = hiltViewModel()
    TrainingScreen(viewModel)
}