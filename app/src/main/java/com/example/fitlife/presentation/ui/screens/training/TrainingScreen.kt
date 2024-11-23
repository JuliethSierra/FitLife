package com.example.fitlife.presentation.ui.screens.training

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.presentation.ui.screens.menu.BottomNavigationBar
import com.example.fitlife.presentation.viewmodel.ExerciseViewModel

@Composable
fun TrainingScreen(viewModel: ExerciseViewModel) {
    val exercises = viewModel.exercises.observeAsState(emptyList())
    Log.d("TrainingScreen", "Ejercicios en la UI: ${exercises.value.size}")

    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
        ) {
            Text(
                text = "Entrenamiento",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Estado de carga
            if (exercises.value.isEmpty()) {
                Text(
                    text = "Cargando ejercicios...",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.body1
                )
            } else {
                // Lista de ejercicios
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(exercises.value) { exercise ->
                        TrainingCard(
                            title = exercise.name,
                            gifUrl = exercise.gifUrl
                        )
                    }
                }
            }
        }
    }
}