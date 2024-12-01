package com.example.fitlife.data.local.mappers

import com.example.fitlife.data.local.entity.ExerciseEntity
import com.example.fitlife.data.local.entity.UserEntity
import com.example.fitlife.domain.model.Exercise
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
        completedExercises = completedExercises
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
        completedExercises = completedExercises
    )
}

fun Exercise.toExerciseEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        name = name,
        gifUrl = gifUrl,
        bodyPart = bodyPart,
        equipment = equipment,
        target = target,
        secondaryMuscles = secondaryMuscles,
        instructions = instructions
    )
}

fun ExerciseEntity.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        gifUrl = gifUrl,
        bodyPart = bodyPart,
        equipment = equipment,
        target = target,
        secondaryMuscles = secondaryMuscles,
        instructions = instructions
    )
}