package com.example.fitlife.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class FitLifeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}