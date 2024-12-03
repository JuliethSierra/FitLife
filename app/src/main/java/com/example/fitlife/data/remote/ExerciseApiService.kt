package com.example.fitlife.data.remote

import com.example.fitlife.domain.model.Exercise
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ExerciseApiService {

    @GET("exercises/name/{name}")
    suspend fun getExerciseByName(
        @Path("name") name: String,
        @Header("X-RapidAPI-Key") apiKey: String = "9800ce8605msh72660138efba265p1aadb3jsn482b92f7563c",
        @Header("X-RapidAPI-Host") apiHost: String = "exercisedb.p.rapidapi.com"
    ): List<Exercise>

    @GET("exercises")
    suspend fun getAllExercises(
        @Header("X-RapidAPI-Key") apiKey: String = "9800ce8605msh72660138efba265p1aadb3jsn482b92f7563c",
        @Header("X-RapidAPI-Host") apiHost: String = "exercisedb.p.rapidapi.com"
    ): List<Exercise>
}