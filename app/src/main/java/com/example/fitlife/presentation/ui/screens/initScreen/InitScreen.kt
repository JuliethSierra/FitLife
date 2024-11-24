package com.example.fitlife.presentation.ui.screens.initScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InitScreen() {
    val purple = Color(0xFFAC44F2)
    val orange = Color(0xFFF28B0C)
    val white = Color(0xFFFFFFFF)
    val lightGrayBlue = Color(0xFF88A5BF)

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
                text = "Hola Andrea!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }

        // Barra de progreso
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(white, RoundedCornerShape(8.dp))
                .border(2.dp, orange, RoundedCornerShape(8.dp))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Progreso",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "61%",
                style = MaterialTheme.typography.bodyLarge,
                color = lightGrayBlue
            )
        }

        // Sección "En el último mes"
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white, RoundedCornerShape(12.dp))
                .border(2.dp, orange, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "EN EL ÚLTIMO MES",
                style = MaterialTheme.typography.titleLarge,
                color = orange,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(value = "6", label = "Completos", backgroundColor = lightGrayBlue, textColor = white)
                StatItem(value = "0", label = "Hits", backgroundColor = lightGrayBlue, textColor = white)
            }
        }
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

@Preview
@Composable
fun ProgressScreenPreview() {
    InitScreen()
}

