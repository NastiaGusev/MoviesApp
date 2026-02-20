package com.example.data.di

import android.util.Log
import com.example.data.remote.api.MoviesApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("apiKey") apiKey: String
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor { message ->
            try {
                if (message.startsWith("{") || message.startsWith("[")) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jsonElement = gson.fromJson(message, Any::class.java)

                    val prettyJson = gson.toJson(jsonElement)
                    Log.d("API_LOG", "Response:\n$prettyJson")
                } else {
                    Log.d("API_LOG", message)
                }
            } catch (e: Exception) {
                Log.d("API_LOG", "Error formatting JSON response: $message")
            }
        }

        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(client: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }
}
