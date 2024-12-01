package com.example.fitlife.data.local.mappers

import com.example.fitlife.data.local.entity.UserEntity
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum


fun User.toUserEntity(): UserEntity {
    return UserEntity(
        name = name,
        lastName = lastName,
        email = email,
        height = height,
        birthDate = birthDate,
        gender = gender.name,
        weight = weight,
        profilePictureUrl = profilePictureUrl,
        numberPhone = numberPhone,
        password = password,
        uid = uid,
        completedExercises = completedExercises.joinToString(",")
    )
}

fun UserEntity.toUser(): User {
    return User(
        name = name,
        lastName = lastName,
        email = email,
        height = height,
        birthDate = birthDate,
        gender = GenderEnum.valueOf(gender.uppercase()), // Convierte String a GenderEnum
        weight = weight,
        profilePictureUrl = profilePictureUrl,
        numberPhone = numberPhone,
        password = password,
        uid = uid,
        completedExercises = completedExercises.split(",").filter { it.isNotBlank() }
    )
}