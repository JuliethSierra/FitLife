package com.example.fitlife.data.repository

import android.util.Log
import androidx.lifecycle.map
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.entity.ExerciseEntity
import com.example.fitlife.data.local.mappers.toExercise
import com.example.fitlife.data.local.mappers.toExerciseEntity
import com.example.fitlife.data.remote.ExerciseApiService
import com.example.fitlife.data.remote.firebase.FirebaseClient
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.domain.model.Exercise
import com.example.fitlife.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseRemoteDataSource: ExerciseApiService,
    private val userService: UserService,
    private val userDao: UserDao
) : ExerciseRepository {

    /*    override suspend fun getExerciseByName(name: String): List<Exercise> {
            return withContext(Dispatchers.IO) {
                try {
                    // Intenta obtener datos locales
                    val localExercise = userDao.getLocalExerciseByName(name).map { it.toExercise() }
                    Log.d("ExerciseViewModel", "Local Exercise: $localExercise")

                    // Si no hay datos locales, consulta la API
                    return@withContext if (localExercise.isEmpty()) {
                        val apiExercise = exerciseRemoteDataSource.getExerciseByName(name).firstOrNull()
                        Log.d("ExerciseViewModel", "API Exercise: $apiExercise")

                        // Si la API devuelve un resultado, lo retornamos
                        apiExercise?.let {
                            listOf(it) // Retornar como lista para que coincida con el tipo esperado
                        } ?: emptyList() // Si la API también falla, retornar una lista vacía
                    } else {
                        // Retorna datos locales si existen
                        localExercise
                    }
                } catch (e: Exception) {
                    Log.e("ExerciseViewModel", "Error al obtener el ejercicio: ${e.message}", e)
                    emptyList() // En caso de error, retornar una lista vacía
                }
            }
        }
     */

    override suspend fun getExerciseByName(name: String): List<Exercise> {
        return try {
            // Obtener el ejercicio desde la API
            //val apiExercise = exerciseRemoteDataSource.getExerciseByName(name).firstOrNull()
            // Ejecutar la consulta de la base de datos en un hilo secundario
            val localExercises = userDao.getLocalExerciseByName(name)

            Log.d("ExerciseViewModel", "local: $localExercises")
            if (localExercises.isNotEmpty()) {
                // Si se encontró, convertir a dominio y devolverlo
                localExercises.map { entity -> entity.toExercise() }
            } else {
                Log.d("ExerciseViewModel", "Api: apiiiiiiiiii")
                // Si no se encontró en la base de datos, retornar lista vacía
                exerciseRemoteDataSource.getExerciseByName(name)
            }
        } catch (e: Exception) {
            // Capturar cualquier error, loguearlo y retornar lista vacía
            Log.e("ExerciseViewModel", "Error al obtener el ejercicio: ${e.message}", e)
            emptyList() // Retornar una lista vacía en caso de error
        }
    }


    /*override suspend fun getExerciseByName(name: String): List<Exercise> {
    return try {
        // Primero, buscar en los datos locales
        val localExercises = withContext(Dispatchers.IO) {
            userDao.getLocalExerciseByName(name)
        }

        // Si se encuentra en los datos locales, devolver la lista de ejercicios
        if (localExercises.isNotEmpty()) {
            Log.d("ExerciseViewModel", "Ejercicio encontrado en la base de datos local")
            localExercises.map { entity -> entity.toExercise() }
        } else {
            // Si no se encuentra en los datos locales, buscar en la API
            Log.d("ExerciseViewModel", "Ejercicio no encontrado en la base de datos local, buscando en la API")
            val apiExercise = exerciseRemoteDataSource.getExerciseByName(name).firstOrNull()

            // Si se encuentra en la API, retornar la lista vacía (o manejar la conversión si es necesario)
            if (apiExercise != null) {
                // Opcional: guardar en la base de datos local si lo deseas
                userDao.insertExercises(apiExercise.toExerciseEntity())  // Inserta el ejercicio desde la API si es necesario
                listOf(apiExercise)
            } else {
                // Si no se encuentra ni en la base de datos local ni en la API, retornar lista vacía
                emptyList()
            }
        }
    } catch (e: Exception) {
        // Capturar cualquier error, loguearlo y retornar lista vacía
        Log.e("ExerciseViewModel", "Error al obtener el ejercicio: ${e.message}", e)
        emptyList() // Retornar una lista vacía en caso de error
    }
}*/

    override suspend fun getAllExercises(): List<Exercise> {
        var exercises = getAllLocalExercises()
        if (exercises.isEmpty()) {
            val remoteExercises = exerciseRemoteDataSource.getAllExercises()
            addExercises(remoteExercises)
            exercises = getAllLocalExercises()
        }
        return exercises
    }

    override suspend fun getAllLocalExercises(): List<Exercise> {
        val localExercises = userDao.getAllLocalExercises()
        val exercises = localExercises.map { it.toExercise() }
        return exercises
    }

    override suspend fun addExercises(exercise: List<Exercise>) {
        exercise.map { userDao.insertExercises(it.toExerciseEntity()) }
    }

    override suspend fun addCompletedExercise(exerciseName: String): Boolean =
        userService.addCompletedExercise(AuthRepositoryImpl.currentUser?.uid ?: "", exerciseName)

    override suspend fun getCompletedExercises(): List<String> =
        userService.getCompletedExercises(AuthRepositoryImpl.currentUser?.uid ?: "")
}