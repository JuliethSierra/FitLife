package com.example.fitlife.presentation.ui.screens.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.example.fitlife.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun SignInScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel
) {
    val name = remember { mutableStateOf("Andrea") }
    val lastName = remember { mutableStateOf("Sierra") }
    val email = remember { mutableStateOf("a@s.com") }
    val height = remember { mutableStateOf("164") }
    val birthDate = remember { mutableStateOf("2002/05/22") }
    val gender = remember { mutableStateOf("F") }
    val weight = remember { mutableStateOf("60") }
    val numberPhone = remember { mutableStateOf("3219834716") }
    val password = remember { mutableStateOf("12345678") }
    val confirmPassword = remember { mutableStateOf("12345678") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "¡Regístrate!",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }

            item {
                InputField(valueState = name, labelId = "Nombre")
            }

            item {
                InputField(valueState = lastName, labelId = "Apellido")
            }

            item {
                EmailInput(emailState = email)
            }

            item {
                InputField(valueState = height, labelId = "Altura (cm)", keyboardType = KeyboardType.Number)
            }

            item {
                InputField(valueState = weight, labelId = "Peso (kg)", keyboardType = KeyboardType.Number)
            }

            item {
                InputField(valueState = birthDate, labelId = "Fecha de Nacimiento (yyyy/MM/dd)")
            }

            item {
                InputField(valueState = gender, labelId = "Género")
            }

            item {
                InputField(valueState = numberPhone, labelId = "Número de Teléfono", keyboardType = KeyboardType.Phone)
            }

            item {
                PasswordInput(
                    passwordState = password,
                    labelId = "Contraseña"
                )
            }

            item {
                PasswordInput(
                    passwordState = confirmPassword,
                    labelId = "Confirmar Contraseña"
                )
            }

            item {
                SubmitButton(
                    textId = "Registrar",
                    inputValid = name.value.isNotBlank() &&
                            lastName.value.isNotBlank() &&
                            email.value.isNotBlank() &&
                            height.value.isNotBlank() &&
                            weight.value.isNotBlank() &&
                            birthDate.value.isNotBlank() &&
                            gender.value.isNotBlank() &&
                            numberPhone.value.isNotBlank() &&
                            password.value.isNotBlank() &&
                            confirmPassword.value.isNotBlank() &&
                            password.value == confirmPassword.value
                ) {
                    val user = User(
                        name = name.value,
                        lastName = lastName.value,
                        email = email.value,
                        height = height.value.toFloatOrNull() ?: 0f,
                        birthDate = birthDate.value,
                        gender = GenderEnum.values().find { it.name.equals(gender.value, true) }
                            ?: GenderEnum.MALE,
                        weight = weight.value.toFloatOrNull() ?: 0f,
                        numberPhone = numberPhone.value,
                        profilePictureUrl = null,
                        uid = "",
                        password = password.value
                    )

                    coroutineScope.launch {
                        val success = signUpViewModel.signUp(user)
                        if (success) {
                            Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                            navController.navigate("LogIn")
                        } else {
                            Toast.makeText(context, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    val visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()) {
                PasswordVisibleIcon(passwordVisible)
            }
        }
    )
}

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image = if (passwordVisible.value) Icons.Default.VisibilityOff else Icons.Default.Visibility
    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
        Icon(imageVector = image, contentDescription = null)
    }
}

@Composable
fun SubmitButton(
    textId: String,
    inputValid: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValid
    ) {
        Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}