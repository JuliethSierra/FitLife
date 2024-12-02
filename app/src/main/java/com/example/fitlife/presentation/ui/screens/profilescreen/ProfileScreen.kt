package com.example.fitlife.presentation.ui.screens.profilescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.example.fitlife.presentation.ui.screens.utils.Util
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import com.example.fitlife.presentation.viewmodel.UserViewModel
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.yellowAccent

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    navController: NavController,
    logInViewModel: LogInScreenViewModel,
    viewModel: UserViewModel
) {
    val user = AuthRepositoryImpl.currentUser
    var showEditDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(user?.name ?: "") }
    var lastName by remember { mutableStateOf(user?.lastName ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var height  by remember { mutableStateOf(user?.weight.toString()) }
    var birthDate by remember { mutableStateOf(user?.birthDate ?: "") }
    var gender by remember { mutableStateOf(user?.gender ?: GenderEnum.MALE) }  // Ajusta el valor predeterminado según tu enum
    var weight by remember { mutableStateOf(user?.weight.toString()) }
    var phone by remember { mutableStateOf(user?.numberPhone ?: "") }
    var password by remember { mutableStateOf(user?.password ?: "") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(purple, yellowAccent)
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "FitLife",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = { showEditDialog = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .border(2.dp, yellowAccent, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = purple
                    )
                }
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

        // Información adicional
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            item {
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
                            .padding(4.dp)
                            .width(100.dp)
                            .height(110.dp)
                    ) {
                        UserDataCard("${user?.weight} kg", Color.Black, "Peso", yellowAccent)
                    }
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color.White,
                        elevation = 8.dp,
                        modifier = Modifier
                            .padding(4.dp)
                            .width(100.dp)
                            .height(110.dp)
                    ) {
                        UserDataCard("${user?.height} cm", Color.Black, "Estatura", yellowAccent)
                    }
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color.White,
                        elevation = 8.dp,
                        modifier = Modifier
                            .padding(4.dp)
                            .width(100.dp)
                            .height(110.dp)
                    ) {
                        UserDataCard("${Util.calculateIMC(user?.weight, user?.height)}", Color.Black, "IMC", yellowAccent)
                    }
                }
            }
            item {
                AdditionalInfoCard(
                    icon = Icons.Default.Email,
                    label = "Correo",
                    value = "${user?.email}"
                )
            }
            item {
                AdditionalInfoCard(
                    icon = Icons.Default.CalendarToday,
                    label = "Fecha de nacimiento",
                    value = "${user?.birthDate}"
                )
            }
            item{
                if (user != null) {
                    AdditionalInfoCard(
                        icon = Icons.Default.CalendarToday,
                        label = "Edad",
                        value = "${Util.calculateAge(user.birthDate)}"
                    )
                }
            }
            item {
                AdditionalInfoCard(
                    icon = Icons.Default.Person,
                    label = "Sexo",
                    value = "${user?.gender}"
                )
            }
            item {
                AdditionalInfoCard(
                    icon = Icons.Default.Phone,
                    label = "Teléfono",
                    value = "${user?.numberPhone}"
                )
            }
            item {
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
        
        // Modal de edición
        if (showEditDialog) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text(text = "Editar Información", style = MaterialTheme.typography.titleMedium) },
                text = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),  // Una sola columna de celdas
                        modifier = Modifier.padding(2.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        item {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Nombre") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = { lastName = it },
                                label = { Text("Apellido") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Correo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = height,
                                onValueChange = { height = it },
                                label = { Text("Altura") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = birthDate,
                                onValueChange = { birthDate = it },
                                label = { Text("Fecha de Nacimiento") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = gender.name,
                                onValueChange = { gender = GenderEnum.valueOf(it) },
                                label = { Text("Género") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = weight,
                                onValueChange = { weight = it },
                                label = { Text("Peso") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("Teléfono") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Contraseña") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                },
                confirmButton = {
                    FloatingActionButton(
                        onClick = {
                            showEditDialog = false
                            viewModel.updateUser(
                                User(
                                    name = name,
                                    lastName = lastName,
                                    email = email,
                                    height = height.toFloatOrNull() ?: 0f,
                                    birthDate = birthDate,
                                    gender = gender,
                                    weight = weight.toFloatOrNull() ?: 0f,
                                    profilePictureUrl = null,
                                    numberPhone = phone,
                                    password = password,
                                    uid = user?.uid ?: "",
                                    completedExercises = emptyList()
                                )
                            )
                            logInViewModel.signOut()
                            navController.navigate("introduction")
                        },
                        backgroundColor = purple,
                        contentColor = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp)
                            .width(100.dp)
                    ) {
                        Text("Guardar", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                    }
                },
                dismissButton = {
                    FloatingActionButton(
                        onClick = { showEditDialog = false },
                        backgroundColor = Color.White,
                        contentColor = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp)
                            .border(2.dp, purple, RoundedCornerShape(16.dp))
                            .width(100.dp)
                    ) {
                        Text("Cancelar")
                    }
                },
                modifier = Modifier.width(340.dp)
            )
        }
    }
}
@Composable
fun AdditionalInfoCard(icon: ImageVector, label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
            .background(Color.White)
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = yellowAccent,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
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
            color = keyColor,
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
