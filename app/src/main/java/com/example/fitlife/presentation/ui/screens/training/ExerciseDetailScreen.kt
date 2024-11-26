package com.example.fitlife.presentation.ui.screens.training

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitlife.presentation.viewmodel.ExerciseViewModel

@Composable
fun ExerciseDetailScreen(
    exerciseName: String,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val exerciseDetail by viewModel.exerciseDetail.observeAsState()

    LaunchedEffect(exerciseName) {
        viewModel.fetchExerciseByName(exerciseName)
    }

    if (exerciseDetail != null) {
        Scaffold { paddingValues ->
            Text(
                text = "Ejercicio: ${exerciseDetail!!.name}",
                modifier = Modifier.padding(paddingValues)
            )
        }
    } else {
        Text(text = "Cargando detalles del ejercicio...")
    }
}

