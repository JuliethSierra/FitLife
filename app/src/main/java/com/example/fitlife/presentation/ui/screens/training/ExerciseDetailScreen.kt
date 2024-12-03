package com.example.fitlife.presentation.ui.screens.training

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import coil.request.ImageRequest
import com.example.fitlife.presentation.viewmodel.ExerciseViewModel
import com.example.fitlife.presentation.viewmodel.UserViewModel
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import coil.decode.ImageDecoderDecoder
import com.example.fitlife.ui.theme.purple
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ExerciseDetailScreen(
    exerciseName: String,
    viewModel: ExerciseViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(exerciseName) {
        viewModel.fetchExerciseByName(exerciseName)
        Log.d("ExerciseViewModel", "Ejercicio cargado en fetchExerciseByName para: $exerciseName")

    }

// Observa el estado desde el ViewModel
    val exerciseDetail by viewModel.uiState.collectAsState()
    Log.d("ExerciseDetailScreen", "Estado actual del ejercicio: $exerciseDetail")

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Log.d("ExerciseViewModel", "uistatescreen exercise: ${exerciseDetail.exercise}")
        Log.d("ExerciseViewModel", "uistatescreen exerciseList: ${exerciseDetail.exerciseList}")
        Log.d("ExerciseViewModel", "uistatescreen loading: ${exerciseDetail.isLoading}")
        if (exerciseDetail.exercise != null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = exerciseDetail!!.exercise!!.name,
                    style = MaterialTheme.typography.h5.copy(color = purple), // Color morado
                    modifier = Modifier
                        .fillMaxWidth() // Asegura que el texto ocupe el ancho completo
                        .align(Alignment.CenterHorizontally)// Centra el texto horizontalmente
                )
                // Imagen GIF del ejercicio
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(exerciseDetail!!.exercise!!.gifUrl)
                        .decoderFactory(ImageDecoderDecoder.Factory())
                        .build(),
                    contentDescription = "Exercise GIF",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                // Instrucciones
                exerciseDetail!!.exercise!!.instructions?.let { instructions ->
                    Text(
                        text = "Instructions:\n${instructions.joinToString("\n")}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Músculos secundarios
                exerciseDetail!!.exercise!!.secondaryMuscles?.let { muscles ->
                    Text(
                        text = "Muscles:\n${muscles.joinToString("\n")}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Botón de finalizar ejercicio
                Button(
                    onClick = {
                        scope.launch {
                            userViewModel.addCompletedExercise(exerciseName)
                            snackbarHostState.showSnackbar("Ejercicio finalizado con éxito")
                            onNavigateBack() // Navegar hacia atrás
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Finalizar Ejercicio")
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}