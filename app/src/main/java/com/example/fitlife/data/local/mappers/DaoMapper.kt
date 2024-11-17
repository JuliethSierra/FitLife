package com.example.fitlife.data.local.mappers

import com.example.fitlife.data.local.entity.UserEntity
import com.example.fitlife.domain.model.User


fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        fullName = fullName,
        email = email,
        height = height,
        birthDate = birthDate,
        gender = gender,
        weight = weight,
        profilePictureUrl = profilePictureUrl,
        createdAt = createdAt,
        lastLogin = lastLogin
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        fullName = fullName,
        email = email,
        height = height,
        birthDate = birthDate,
        gender = gender,
        weight = weight,
        profilePictureUrl = profilePictureUrl,
        createdAt = createdAt,
        lastLogin = lastLogin
    )
}