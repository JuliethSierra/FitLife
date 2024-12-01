package com.example.fitlife.presentation.ui.screens.profilescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.fitlife.data.repository.AuthRepositoryImpl
import com.example.fitlife.presentation.ui.screens.utils.Util
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import com.example.fitlife.ui.theme.lightGrayBlue
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.yellowAccent

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    navController: NavController,
    logInViewModel: LogInScreenViewModel
) {

    val user = AuthRepositoryImpl.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    .background(Brush.radialGradient(listOf(yellowAccent, purple)))
                    .padding(4.dp),
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
                text = "${user?.name + " " + user?.lastName}",
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
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                elevation = 8.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .width(100.dp)
            ) {
                UserDataCard("${user?.weight} kg", Color.Black, "Peso", yellowAccent)
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                elevation = 8.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .width(100.dp)
            ) {
                UserDataCard("${user?.height} cm", Color.Black, "Estatura", yellowAccent)
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                elevation = 8.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .width(100.dp)
            ) {
                UserDataCard("${Util.calculateIMC(user?.weight, user?.height)}", Color.Black, "IMC", yellowAccent)
            }
        }

        // Información adicional
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AdditionalInfoItem(Icons.Default.Email, "Correo", "${user?.email}", yellowAccent)
            Divider(color = lightGrayBlue, thickness = 1.dp)
            AdditionalInfoItem(Icons.Default.CalendarToday, "Fecha de nacimiento", "${user?.birthDate}", yellowAccent)
            Divider(color = lightGrayBlue, thickness = 1.dp)
            if (user != null) {
                AdditionalInfoItem(Icons.Default.CalendarToday, "}edad", "${Util.calculateAge(user.birthDate)}", yellowAccent)
                Divider(color = lightGrayBlue, thickness = 1.dp)
            }
            AdditionalInfoItem(Icons.Default.Person, "Sexo", "${user?.gender}", yellowAccent)
            Divider(color = lightGrayBlue, thickness = 1.dp)
            AdditionalInfoItem(Icons.Default.Phone, "Telefono", "${user?.numberPhone}", yellowAccent)

        }

        // Botón de cerrar sesión
        FloatingActionButton(
            onClick = {
                logInViewModel.signOut()
                navController.navigate("introduction")
            },
            backgroundColor = purple,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
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
fun UserDataCard(value: String, valueColor: Color, key: String, keyColor: Color) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Crear las referencias para los componentes dentro del ConstraintLayout
        val (valueText, labelText) = createRefs()

        // Definir las restricciones para los elementos dentro del ConstraintLayout
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.constrainAs(valueText) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = key,
            color = keyColor, // Opcionalmente puedes modificar el color
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.constrainAs(labelText) {
                top.linkTo(valueText.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
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
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}
