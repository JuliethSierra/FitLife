package com.example.fitlife.presentation.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel


import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.fitlife.presentation.ui.screens.utils.Constants
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.white


@Composable
fun LoginScreen(
    navController: NavController,
    logInViewModel: LogInScreenViewModel
) {
    val emailState = remember { mutableStateOf("a123a@s.com") }
    val passwordState = remember { mutableStateOf("12345678") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val uiState by logInViewModel.uiState.collectAsState()
    var processLogIn by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = white) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (title, emailField, passwordField, loginButton) = createRefs()

            // Centrar el grupo de elementos verticalmente
            val guideline = createGuidelineFromTop(0.3f)

            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineMedium,
                color = purple, // Color morado del tema
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(guideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Correo Electrónico", color = purple)},
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(emailField) {
                        top.linkTo(title.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            OutlinedTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Contraseña", color = purple) },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(passwordField) {
                        top.linkTo(emailField.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password)
            )

            Button(
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = purple,
                    contentColor = white
                ),
                onClick = {
                    Log.d(Constants.TAG, "Start to login")
                    processLogIn = true
                    logInViewModel.login(emailState.value, passwordState.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(loginButton) {
                        top.linkTo(passwordField.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(text = "Iniciar Sesión", color = white)
            }
        }
    }
    LaunchedEffect(key1 = uiState.userUiState) {
        if (processLogIn) {
            if (uiState.userUiState != null) {
                Toast.makeText(context, "Login exitoso", Toast.LENGTH_SHORT).show()
                navController.navigate("InitScreen")
            } else {
                Toast.makeText(context, "Error en el login", Toast.LENGTH_SHORT).show()
            }
            processLogIn = false
        }
    }
}


