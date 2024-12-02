package com.example.fitlife.data.local.mappers

import androidx.room.TypeConverter
import com.example.fitlife.domain.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {
    private val gson = Gson()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(List::class.java)

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
    // Convierte el List<User> a String (JSON)
    @TypeConverter
    fun fromUserList(users: List<User>?): String? {
        return if (users == null) null else jsonAdapter.toJson(users)
    }

    // Convierte el String (JSON) de vuelta a List<User>
    @TypeConverter
    fun toUserList(usersString: String?): List<User>? {
        return if (usersString == null) null else jsonAdapter.fromJson(usersString)?.let {
            it as? List<User> // Convertir explícitamente a List<User>
        }
    }
}


