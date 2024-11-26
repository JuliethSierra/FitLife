package com.example.fitlife.presentation.ui.screens.training

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.fitlife.presentation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.P)
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
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                androidx.compose.material.Text(
                    text = "${exerciseDetail!!.name}",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${exerciseDetail!!.gifUrl}")
                        .decoderFactory(ImageDecoderDecoder.Factory())
                        .build(),
                    contentDescription = "Exercise GIF",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text(
                    text = "Instructions: ${exerciseDetail!!.instructions.joinToString("\n")}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray
                )
                Text(
                    text = "Muscles: ${exerciseDetail!!.secondaryMuscles.joinToString("\n")}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray
                )
            }
        }
    } else {
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        )
    }
}

