package com.example.fitlife.domain.model

data class User(
    val id: String, // Identificador único del usuario (Firebase UID)
    val fullName: String, // Nombre y apellido
    val email: String, // Correo electrónico
    val height: Float, // Estatura en metros o centímetros
    val birthDate: String, // Fecha de nacimiento en formato ISO (e.g., "YYYY-MM-DD")
    val gender: String, // Sexo: "Male", "Female", "Other"
    val weight: Float, // Peso en kilogramos
    val profilePictureUrl: String? = null, // URL de la foto de perfil (opcional)
    val createdAt: Long = System.currentTimeMillis(), // Fecha de registro
    val lastLogin: Long? = null // Último inicio de sesión (opcional)
)
