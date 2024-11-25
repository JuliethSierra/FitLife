package com.example.fitlife.data.remote.firebase.mappers

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.model.enums.GenderEnum
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toUser(): User {
    return User(
        uid = getString("uid") ?: "",
        name = getString("name") ?: "",
        lastName = getString("lastName") ?: "",
        email = getString("email") ?: "",
        height = getDouble("height")?.toFloat() ?: 0f,
        birthDate = getString("birthDate") ?: "",
        gender = GenderEnum.valueOf(getString("gender") ?: GenderEnum.OTHER.name),
        weight = getDouble("weight")?.toFloat() ?: 0f,
        profilePictureUrl = getString("profilePictureUrl") ?: "",
        password = getString("password") ?: "",
        numberPhone = getString("numberPhone") ?: ""
    )
}