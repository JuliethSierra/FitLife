package com.example.fitlife.presentation.ui.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import com.example.fitlife.presentation.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    logInViewModel: LogInScreenViewModel,
    onLogInSuccess: () -> Unit
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm.value) {
                Text(text = "Inicia Sesión")
                UserForm(isCreateAccount = false
                ) {
                  email, password ->
                    Log.d("FitLife", "LogIn with $email y $password")
                    logInViewModel.signInWithEmailAndPassword(email, password, onLogInSuccess)
                }
            } else {
                Text(text = "Crea una Cuenta")
                UserForm(isCreateAccount = true) { email, password ->
                    Log.d("FitLife", "SignIn with $email y $password")
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val askAccount = if (showLoginForm.value) "¿No tienes cuenta?" else "¿Ya tienes cuenta?"
                val signInOrLogIn = if (showLoginForm.value) "Regístrate" else "Inicia Sesión"
                Text(text = askAccount)
                Text(
                    text = signInOrLogIn,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val confirmPasswordVisible = rememberSaveable { mutableStateOf(false) }

    val nombre = rememberSaveable { mutableStateOf("") }
    val apellido = rememberSaveable { mutableStateOf("") }
    val telefono = rememberSaveable { mutableStateOf("") }
    val fechaNacimiento = rememberSaveable { mutableStateOf("") }
    val sexo = rememberSaveable { mutableStateOf("") }
    val peso = rememberSaveable { mutableStateOf("") }
    val estatura = rememberSaveable { mutableStateOf("") }

    val valid = remember(
        email.value,
        password.value,
        confirmPassword.value,
        nombre.value,
        apellido.value,
        telefono.value,
        fechaNacimiento.value,
        sexo.value,
        peso.value,
        estatura.value
    ) {
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty() &&
                (!isCreateAccount || (
                        nombre.value.trim().isNotEmpty() &&
                                apellido.value.trim().isNotEmpty() &&
                                telefono.value.trim().isNotEmpty() &&
                                fechaNacimiento.value.trim().isNotEmpty() &&
                                sexo.value.trim().isNotEmpty() &&
                                peso.value.trim().isNotEmpty() &&
                                estatura.value.trim().isNotEmpty() &&
                                confirmPassword.value.trim().isNotEmpty() &&
                                password.value == confirmPassword.value
                        ))
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) {
            InputField(valueState = nombre, labelId = "Nombre", keyboardType = KeyboardType.Text)
            InputField(valueState = apellido, labelId = "Apellido", keyboardType = KeyboardType.Text)
            InputField(valueState = telefono, labelId = "Número de Teléfono", keyboardType = KeyboardType.Phone)
            InputField(valueState = fechaNacimiento, labelId = "Fecha de Nacimiento", keyboardType = KeyboardType.Text)
            InputField(valueState = sexo, labelId = "Sexo", keyboardType = KeyboardType.Text)
            InputField(valueState = peso, labelId = "Peso (kg)", keyboardType = KeyboardType.Number)
            InputField(valueState = estatura, labelId = "Estatura (cm)", keyboardType = KeyboardType.Number)
        }

        EmailInput(emailState = email)
        PasswordInput(
            passwordState = password,
            labelId = "Contraseña",
            passwordVisible = passwordVisible
        )

        if (isCreateAccount) {
            PasswordInput(
                passwordState = confirmPassword,
                labelId = "Confirmar Contraseña",
                passwordVisible = confirmPasswordVisible
            )
        }

        SubmitButton(
            textId = if (isCreateAccount) "Crear Cuenta" else "Iniciar Sesión",
            inputValid = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

/*
* @Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String, String, String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _, _, _, _ -> }
){
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val confirmPasswordVisible = rememberSaveable { mutableStateOf(false) }

    val nombre = rememberSaveable { mutableStateOf("") }
    val apellido = rememberSaveable { mutableStateOf("") }
    val telefono = rememberSaveable { mutableStateOf("") }
    val fechaNacimiento = rememberSaveable { mutableStateOf("") }
    val sexo = rememberSaveable { mutableStateOf("") }
    val peso = rememberSaveable { mutableStateOf("") }
    val estatura = rememberSaveable { mutableStateOf("") }

    val valid = remember(
        email.value,
        password.value,
        confirmPassword.value,
        nombre.value,
        apellido.value,
        telefono.value,
        fechaNacimiento.value,
        sexo.value,
        peso.value,
        estatura.value
    ) {
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty() &&
                (!isCreateAccount || (
                        nombre.value.trim().isNotEmpty() &&
                                apellido.value.trim().isNotEmpty() &&
                                telefono.value.trim().isNotEmpty() &&
                                fechaNacimiento.value.trim().isNotEmpty() &&
                                sexo.value.trim().isNotEmpty() &&
                                peso.value.trim().isNotEmpty() &&
                                estatura.value.trim().isNotEmpty() &&
                                confirmPassword.value.trim().isNotEmpty() &&
                                password.value == confirmPassword.value
                        ))
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) {
            InputField(valueState = nombre, labelId = "Nombre", keyboardType = KeyboardType.Text)
            InputField(valueState = apellido, labelId = "Apellido", keyboardType = KeyboardType.Text)
            InputField(valueState = telefono, labelId = "Número de Teléfono", keyboardType = KeyboardType.Phone)
            InputField(valueState = fechaNacimiento, labelId = "Fecha de Nacimiento", keyboardType = KeyboardType.Text)
            InputField(valueState = sexo, labelId = "Sexo", keyboardType = KeyboardType.Text)
            InputField(valueState = peso, labelId = "Peso (kg)", keyboardType = KeyboardType.Number)
            InputField(valueState = estatura, labelId = "Estatura (cm)", keyboardType = KeyboardType.Number)
        }

        EmailInput(emailState = email)
        PasswordInput(
            passwordState = password,
            labelId = "Contraseña",
            passwordVisible = passwordVisible
        )

        if (isCreateAccount) {
            PasswordInput(
                passwordState = confirmPassword,
                labelId = "Confirmar Contraseña",
                passwordVisible = confirmPasswordVisible
            )
        }

        SubmitButton(
            textId = if (isCreateAccount) "Crear Cuenta" else "Iniciar Sesión",
            inputValid = valid
        ) {
            if (isCreateAccount) {
                onDone(
                    email.value.trim(),
                    password.value.trim(),
                    nombre.value.trim(),
                    apellido.value.trim(),
                    telefono.value.trim(),
                    fechaNacimiento.value.trim(),
                    sexo.value.trim(),
                    peso.value.trim(),
                    estatura.value.trim()
                )
            } else {
                onDone(email.value.trim(), password.value.trim(), "", "", "", "", "", "", "")
            }
            keyboardController?.hide()
        }
    }
}*/

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

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image = if (passwordVisible.value) Icons.Default.VisibilityOff else Icons.Default.Visibility
    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
        Icon(imageVector = image, contentDescription = null)
    }
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
