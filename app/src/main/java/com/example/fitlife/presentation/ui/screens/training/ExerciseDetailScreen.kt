package com.example.fitlife.presentation.ui.screens.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) {
                    AsyncImage(
                        model = "${exerciseDetail!!.gifUrl}",
                        contentDescription = "Exercise Image",
                    )
                    Text(
                        text = "${exerciseDetail!!.name}",
                        style = MaterialTheme.typography.h5,
                    )
                    Text(text = "Instructions: ${exerciseDetail!!.instructions.joinToString("\n")}")
                }
            }
        }
    } else {
        Text(text = "Cargando detalles del ejercicio...")
    }
}

