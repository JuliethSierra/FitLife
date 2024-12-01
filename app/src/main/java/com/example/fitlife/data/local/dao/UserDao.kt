package com.example.fitlife.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitlife.data.local.entity.UserEntity

@Dao
interface   UserDao {

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE uid = :userId")
    fun getUserById(userId: String): LiveData<UserEntity>

    @Query("UPDATE user_table SET completedExercises = :completedExercises WHERE uid = :userId")
    suspend fun updateCompletedExercises(userId: String, completedExercises: String)
}
