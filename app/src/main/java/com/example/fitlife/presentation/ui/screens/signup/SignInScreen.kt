package com.example.fitlife.presentation.ui.screens.signup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.example.fitlife.presentation.viewmodel.SignUpViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.fitlife.R
import com.example.fitlife.presentation.ui.components.buttons.SubmitButton
import com.example.fitlife.presentation.ui.components.dropdownmenu.GenderDropdownMenu
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.white

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignInScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel
) {

    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterLastName = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterHeight = remember { FocusRequester() }
    val focusRequesterBirthDate = remember { FocusRequester() }
    val focusRequesterGender = remember { FocusRequester() }
    val focusRequesterWeight = remember { FocusRequester() }
    val focusRequesterPhone = remember { FocusRequester() }
    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterConfirmPassword = remember { FocusRequester() }


    val name = remember { mutableStateOf("Andrea") }
    val lastName = remember { mutableStateOf("Sierra") }
    val email = remember { mutableStateOf("a@s.com") }
    val height = remember { mutableStateOf("164") }
    val birthDate = remember { mutableStateOf("2002/05/22") }
    val gender = remember { mutableStateOf(GenderEnum.FEMALE) }
    val weight = remember { mutableStateOf("60") }
    val numberPhone = remember { mutableStateOf("3219834716") }
    val password = remember { mutableStateOf("12345678") }
    val confirmPassword = remember { mutableStateOf("12345678") }

    val uiState by signUpViewModel.uiState.collectAsState()
    var processSignUp by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier.fillMaxSize(), color = white) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.splash),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(100.dp)
            )
            Text(
                text = "Crea tu cuenta",
                style = MaterialTheme.typography.h4,
                color = purple,
                modifier = Modifier.padding(bottom = 24.dp) // Separación del formulario
            )
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    InputField(
                        valueState = name,
                        labelId = "Nombre",
                        focusRequester = focusRequesterName,
                        onNext = { focusRequesterLastName.requestFocus() }
                    )
                }
                item {
                    InputField(
                        valueState = lastName,
                        labelId = "Apellido",
                        focusRequester = focusRequesterLastName,
                        onNext = { focusRequesterEmail.requestFocus() }
                    )
                }
                item {
                    EmailInput(
                        emailState = email,
                        labelId = "Correo Electrónico",
                        focusRequester = focusRequesterEmail,
                        onNext = { focusRequesterHeight.requestFocus() }
                    )
                }
                item {
                    InputField(
                        valueState = height,
                        labelId = "Altura (cm)",
                        keyboardType = KeyboardType.Number,
                        focusRequester = focusRequesterHeight,
                        onNext = { focusRequesterWeight.requestFocus() }
                    )
                }
                item {
                    InputField(
                        valueState = weight,
                        labelId = "Peso (kg)",
                        keyboardType = KeyboardType.Number,
                        focusRequester = focusRequesterWeight,
                        onNext = { focusRequesterBirthDate.requestFocus() }
                    )
                }
                item {
                    InputField(
                        valueState = birthDate,
                        labelId = "Fecha de Nacimiento (yyyy/MM/dd)",
                        focusRequester = focusRequesterBirthDate,
                        onNext = { focusRequesterGender.requestFocus() }
                    )
                }
                item {
                    GenderDropdownMenu(
                        selectedGender = gender,
                        labelId = "Género",
                        focusRequester = focusRequesterGender,
                        onNext = { focusRequesterPhone.requestFocus() }
                    )
                }
                item {
                    InputField(
                        valueState = numberPhone,
                        labelId = "Número de Teléfono",
                        keyboardType = KeyboardType.Phone,
                        focusRequester = focusRequesterPhone,
                        onNext = { focusRequesterPassword.requestFocus() }
                    )
                }
                item {
                    PasswordInput(
                        passwordState = password,
                        labelId = "Contraseña",
                        focusRequester = focusRequesterPassword,
                        onNext = { focusRequesterConfirmPassword.requestFocus() }
                    )
                }
                item {
                    PasswordInput(
                        passwordState = confirmPassword,
                        labelId = "Confirmar Contraseña",
                        focusRequester = focusRequesterConfirmPassword
                    )
                }

                item {
                    SubmitButton(
                        textId = stringResource(id = R.string.text_sign_up),
                        inputValid = name.value.isNotBlank() &&
                                lastName.value.isNotBlank() &&
                                email.value.isNotBlank() &&
                                height.value.isNotBlank() &&
                                weight.value.isNotBlank() &&
                                birthDate.value.isNotBlank() &&
                                gender.value.name.isNotBlank() &&
                                numberPhone.value.isNotBlank() &&
                                password.value.isNotBlank() &&
                                confirmPassword.value.isNotBlank() &&
                                password.value == confirmPassword.value
                    ) {
                        keyboardController?.hide()
                        val user = User(
                            name = name.value,
                            lastName = lastName.value,
                            email = email.value,
                            height = height.value.toFloatOrNull() ?: 0f,
                            birthDate = birthDate.value,
                            gender = gender.value,
                            weight = weight.value.toFloatOrNull() ?: 0f,
                            numberPhone = numberPhone.value,
                            profilePictureUrl = null,
                            uid = "",
                            password = password.value
                        )

                        signUpViewModel.signUp(user)
                        processSignUp = true
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = uiState.isSuccessful) {
        if (processSignUp) {
            if (uiState.isSuccessful) {
                Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                navController.navigate("LogIn")
            } else {
                Toast.makeText(context, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
            }
            processSignUp = false
        }
    }
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    onNext: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId, color = purple) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = if (onNext != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext?.invoke() },
            onDone = { onNext?.invoke() } // If it's the last field, handle "Done"
        ),
        textStyle = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )
}


@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    focusRequester: FocusRequester = FocusRequester(),
    onNext: (() -> Unit)? = null
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId, color = purple) },
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
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = if (onNext != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext?.invoke() },
            onDone = { onNext?.invoke() }
        ),
        textStyle = MaterialTheme.typography.h5.copy(color = Color.Black)
    )
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email",
    focusRequester: FocusRequester = FocusRequester(),
    onNext: (() -> Unit)? = null
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email,
        focusRequester = focusRequester,
        onNext = onNext
    )
}
