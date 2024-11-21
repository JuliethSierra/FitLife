package com.example.fitlife.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val uid: String, // ID único del usuario
    val name: String, // Nombre
    val lastName: String, // Apellido
    val email: String, // Correo electrónico
    val height: Float, // Estatura en centímetros
    val birthDate: String, // Fecha de nacimiento en formato ISO (e.g., "YYYY-MM-DD")
    val gender: String, // Género: "Male", "Female", "Other"
    val weight: Float, // Peso en kilogramos
    val profilePictureUrl: String? = null, // URL de la foto de perfil (opcional)
    val numberPhone: String, // Número de teléfono
    val password: String, // Contraseña
    val createdAt: Long = System.currentTimeMillis(), // Fecha de registro
    val lastLogin: Long? = null // Último inicio de sesión (opcional)
)
