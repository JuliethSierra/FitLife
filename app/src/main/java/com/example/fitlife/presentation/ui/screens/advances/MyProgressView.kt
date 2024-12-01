package com.example.fitlife.presentation.ui.screens.advances

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitlife.presentation.viewmodel.UserViewModel

@Composable
fun MyProgressView(viewModel: UserViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    val completedExercises = uiState.completedExercises

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (completedExercises.isEmpty()) {
            Text(
                text = "No has completado ningún ejercicio aún.",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(completedExercises) { exercise ->
                    ExerciseItem(exerciseName = exercise)
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(exerciseName: String) {
    Text(
        text = exerciseName,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .padding(16.dp)
    )
}
