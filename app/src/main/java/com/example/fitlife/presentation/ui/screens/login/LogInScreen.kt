package com.example.fitlife.presentation.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import com.example.fitlife.presentation.ui.screens.utils.Constants


@Composable
fun LoginScreen(
    navController: NavController,
    logInViewModel: LogInScreenViewModel
) {
    val emailState = remember { mutableStateOf("a@s.com") }
    val passwordState = remember { mutableStateOf("12345678") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val uiState by logInViewModel.uiState.collectAsState()
    var processLogIn by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Correo Electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password)
                )
            }

            item {
                Button(
                    onClick = {
                        Log.d(Constants.TAG, "Start to login")
                        processLogIn = true
                        Log.d(Constants.TAG, "Login Successful ${processLogIn}")
                        logInViewModel.login(emailState.value, passwordState.value)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Iniciar Sesión")
                }
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


