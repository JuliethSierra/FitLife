package com.example.fitlife.presentation.ui.screens.profilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    logInViewModel: LogInScreenViewModel
) {
    // Definición de colores
    val purple = Color(0xFFAC44F2)
    val blue = Color(0xFF3876F2)
    val lightGrayBlue = Color(0xFF88A5BF)
    val yellowAccent = Color(0xFFF2A007)
    val white = Color(0xFFFFFFFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        // Header con botón de editar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(purple)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "FitLife",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Button(
                onClick = { /* Edit action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = lightGrayBlue),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Foto de perfil y nombre
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(lightGrayBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Foto",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Andrea Sierra",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }

        // Tarjetas de datos del usuario (Peso, Altura, Género)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            UserDataCard("60 kg", purple)
            UserDataCard("160 cm", purple)
            UserDataCard("29%", purple)
        }

        // Información adicional
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AdditionalInfoItem(Icons.Default.Email, "Correo", "andrea@example.com", yellowAccent)
            AdditionalInfoItem(Icons.Default.CalendarToday, "Fecha de nacimiento", "10/01/1995", yellowAccent)
            AdditionalInfoItem(Icons.Default.Person, "Sexo", "Femenino", yellowAccent)
            AdditionalInfoItem(Icons.Default.Phone, "Telefono", "123456789", yellowAccent)

        }

        // Botón de cerrar sesión
        Button(
            onClick = {
                logInViewModel.signOut()
                navController.navigate("introduction")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = purple),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Cerrar Sesión",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun UserDataCard(value: String, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(Color.White)
            .padding(2.dp)
    ) {
        Text(
            text = value,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun AdditionalInfoItem(icon: ImageVector, label: String, value: String, iconColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}
