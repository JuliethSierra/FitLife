package com.example.fitlife.presentation.ui.screens.training

import android.annotation.SuppressLint
import android.os.Build
import android.provider.SyncStateContract.Constants
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.fitlife.presentation.viewmodel.UserViewModel
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ExerciseDetailScreen(
    exerciseName: String,
    viewModel: ExerciseViewModel = hiltViewModel(),
    viewModelUserViewModel: UserViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val exerciseDetail by viewModel.exerciseDetail.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(exerciseName) {
        viewModel.fetchExerciseByName(exerciseName)
    }

    if (exerciseDetail != null) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                androidx.compose.material.Text(
                    text = exerciseDetail!!.name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(exerciseDetail!!.gifUrl)
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
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Muscles: ${exerciseDetail!!.secondaryMuscles.joinToString("\n")}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        Log.d("hola", "Button clicked")

                        // Llamar a addCompletedExercise de forma asincrónica y esperar que termine
                        viewModelUserViewModel.addCompletedExercise(exerciseName)

                        // Una vez la operación asincrónica ha finalizado, puedes mostrar el Snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar("Ejercicio finalizado con éxito")
                            onNavigateBack()  // Navegar después de completar la operación
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Finalizar Ejercicio")
                }

            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colors.primary,
            )
        }
    }
}
