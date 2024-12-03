package com.example.fitlife.presentation.ui.screens.initscreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitlife.data.repository.AuthRepositoryImpl
import com.example.fitlife.ui.theme.lightGrayBlue
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.white
import com.example.fitlife.ui.theme.yellowAccent


@Composable
fun InitScreen(navController: NavController) {
    val context = LocalContext.current

    val user = AuthRepositoryImpl.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header con avatar y nombre
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(purple, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = white,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Hola ${user?.name}!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }

        // Barra de progreso con título visible
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(white, RoundedCornerShape(8.dp))
                .border(2.dp, purple, RoundedCornerShape(8.dp)) // Borde amarillo
                .padding(12.dp) // Espaciado interno
        ) {
            Text(
                text = "Progreso",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Barra de progreso y porcentaje
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = 0.11f, // Ejemplo: 61% de progreso
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp),
                    color = purple,
                    trackColor = lightGrayBlue
                )
                Text(
                    text = "11%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = lightGrayBlue,
                    modifier = Modifier.padding(start = 8.dp) // Espaciado a la izquierda del porcentaje
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white, RoundedCornerShape(12.dp))
                .border(2.dp, purple, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "ACTUALES",
                style = MaterialTheme.typography.titleLarge,
                color = purple,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(value = "${user?.completedExercises?.size ?: "Sin datos"}", label = "Completos", backgroundColor = lightGrayBlue, textColor = white)
                StatItem(value = "0", label = "Hits", backgroundColor = lightGrayBlue, textColor = white)
            }
        }
    }

    BackHandler {
        (context as? android.app.Activity)?.finish()
    }
}

@Composable
fun StatItem(value: String, label: String, backgroundColor: Color, textColor: Color) {
    Column(
        modifier = Modifier
            .size(100.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = textColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}


