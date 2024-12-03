package com.example.fitlife

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.compose.ui.platform.LocalContext
import com.example.fitlife.presentation.ui.screens.login.LoginScreen
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableStateFlow

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenUIElements() {
        // Crear un mock del ViewModel
        val mockViewModel = mockk<LogInScreenViewModel>(relaxed = true)

        // Simular el estado inicial del ViewModel
        val mockUiState = MutableStateFlow(LogInScreenViewModel.UiState())
        every { mockViewModel.uiState } returns mockUiState

        // Configura el Composable
        composeTestRule.setContent {
            // Usamos LocalContext.current para obtener el contexto adecuado para crear el NavController
            val navController = TestNavHostController(LocalContext.current)

            LoginScreen(navController = navController, logInViewModel = mockViewModel)
        }

        // Verifica que los elementos están presentes
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Interactúa con los campos de texto
        composeTestRule.onNodeWithText("Correo Electrónico").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("password123")

        // Haz clic en el botón de iniciar sesión
        composeTestRule.onNodeWithText("Iniciar Sesión").performClick()

        // Verificar que el método login fue llamado con los valores correctos
        verify { mockViewModel.login("test@example.com", "password123") }
    }
}
