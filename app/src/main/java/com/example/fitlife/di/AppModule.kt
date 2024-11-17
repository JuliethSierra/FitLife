package com.example.fitlife.di

import android.content.Context
import androidx.room.Room
import com.example.fitlife.data.local.dao.UserDao
import com.example.fitlife.data.local.database.FitLifeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

}

