package com.example.fitlife.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.entity.ExerciseEntity
import com.example.fitlife.data.local.entity.UserEntity
import com.example.fitlife.data.local.mappers.Converters

@Database(entities = [UserEntity::class, ExerciseEntity::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FitLifeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}