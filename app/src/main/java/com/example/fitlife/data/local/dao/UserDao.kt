package com.example.fitlife.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitlife.data.local.entity.ExerciseEntity
import com.example.fitlife.data.local.entity.UserEntity
import com.example.fitlife.data.local.entity.UsersEntity

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

    @Query("SELECT * FROM exercise_table")
    suspend fun getAllLocalExercises(): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_table WHERE name = :name")
    suspend fun getLocalExerciseByName(name: String): List<ExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exerciseEntity: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(userEntity: List<UsersEntity>)

    @Query("SELECT * FROM users_table")
    suspend fun getAllUsersCommunity(): List<UsersEntity>

}
