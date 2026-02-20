package com.example.data.di

import com.example.data.remote.api.MoviesApi
import com.example.data.repository.MoviesRepositoryImpl
import com.example.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesApi: MoviesApi): MoviesRepository =
        MoviesRepositoryImpl(moviesApi)
}