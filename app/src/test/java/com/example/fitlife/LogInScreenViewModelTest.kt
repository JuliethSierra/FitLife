package com.example.fitlife

import io.mockk.coEvery
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.example.fitlife.domain.model.usecases.LoginUseCase
import com.example.fitlife.domain.model.usecases.IsLoggedUseCase
import com.example.fitlife.domain.model.usecases.SignOutUseCase
import com.example.fitlife.presentation.ui.screens.states.LogInUIState
import com.example.fitlife.presentation.viewmodel.LogInScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class LogInScreenViewModelTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule() // Permite ejecutar LiveData y StateFlow de manera sincronizada.

    private lateinit var viewModel: LogInScreenViewModel

    @MockK
    private lateinit var loginUseCase: LoginUseCase

    @MockK
    private lateinit var isLoggedUseCase: IsLoggedUseCase

    @MockK
    private lateinit var signOutUseCase: SignOutUseCase

    private val testDispatcher = StandardTestDispatcher() // Dispatcher para control manual en pruebas.

    @Before
    fun setUp() {
        MockKAnnotations.init(this) // Inicializa los mocks
        Dispatchers.setMain(testDispatcher) // Configura el dispatcher para pruebas asincrónicas.

        // Inicializa el ViewModel
        viewModel = LogInScreenViewModel(loginUseCase, isLoggedUseCase, signOutUseCase)
    }

    @Test
    fun testLoginSuccess() = runTest {
        // Crear un objeto User simulado con todos los parámetros
        val mockUser = User(
            name = "John",
            lastName = "Doe",
            email = "test@example.com",
            height = 1.80f,
            birthDate = "1990-01-01",
            gender = GenderEnum.MALE,
            weight = 75.0f,
            profilePictureUrl = "http://example.com/profile.jpg",
            numberPhone = "123456789",
            password = "password123",
            uid = "user123",
            completedExercises = listOf("Push-up", "Squat")
        )

        // Simula la respuesta del LoginUseCase: Retorna un User cuando se invoca con el email y password correctos
        coEvery { loginUseCase.invoke("test@example.com", "password123") } returns mockUser

        // Llama al método login del ViewModel
        viewModel.login("test@example.com", "password123")

        // Recolecta el estado del UI
        launch {
            viewModel.uiState.collect { state ->
                // Verificamos que el estado de UI tenga el mockUser retornado
                assertEquals(mockUser, state.userUiState)
            }
        }

        // Avanza hasta que las corrutinas se terminen de ejecutar
        advanceUntilIdle()
    }
}
