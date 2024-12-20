package com.example.fitlife.di

import android.content.Context
import androidx.room.Room
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.database.FitLifeDatabase
import com.example.fitlife.data.remote.ExerciseApiService
import com.example.fitlife.data.remote.firebase.services.AuthenticationService
import com.example.fitlife.data.remote.firebase.services.UserService
import com.example.fitlife.data.repository.AuthRepositoryImpl
import com.example.fitlife.data.repository.ExerciseRepositoryImpl
import com.example.fitlife.domain.repository.AuthRepository
import com.example.fitlife.domain.repository.ExerciseRepository
import com.example.fitlife.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FitLifeDatabase =
        Room.databaseBuilder(
            context,
            FitLifeDatabase::class.java,
            "fitlife_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTaskDao(fitLifeDatabase: FitLifeDatabase): UserDao = fitLifeDatabase.userDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com/") // Cambia según tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseApiService(retrofit: Retrofit): ExerciseApiService {
        return retrofit.create(ExerciseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(apiService: ExerciseApiService, userService: UserService, userDao: UserDao): ExerciseRepository {
        return ExerciseRepositoryImpl(apiService,userService, userDao)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        authenticationService: AuthenticationService,
        userService: UserService,
        userDao: UserDao
    ): AuthRepository {
        return AuthRepositoryImpl(authenticationService, userService, userDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        userDao: UserDao
    ): UserRepository {
        return com.example.fitlife.data.repository.UserRepositoryImpl(userService, userDao)
    }

}

