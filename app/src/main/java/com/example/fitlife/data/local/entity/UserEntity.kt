package com.example.fitlife.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey val id: String,
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
