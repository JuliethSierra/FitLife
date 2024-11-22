package com.example.fitlife.data.remote

import com.example.fitlife.domain.model.Exercise
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ExerciseApiService {

    @GET("exercises/name/{name}")
    suspend fun getExerciseByName(
        @Path("name") name: String,
        @Header("X-RapidAPI-Key") apiKey: String = "a6a50e43femshfdb4989bfceb45bp1bb2c3jsn67598536e04c",
        @Header("X-RapidAPI-Host") apiHost: String = "exercisedb.p.rapidapi.com"
    ): List<Exercise>

    @GET("exercises")
    suspend fun getAllExercises(
        @Header("X-RapidAPI-Key") apiKey: String = "a6a50e43femshfdb4989bfceb45bp1bb2c3jsn67598536e04c",
        @Header("X-RapidAPI-Host") apiHost: String = "exercisedb.p.rapidapi.com"
    ): List<Exercise>
}