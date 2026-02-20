package com.example.di

import com.example.moviesapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY
}