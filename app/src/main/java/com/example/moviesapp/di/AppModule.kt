package com.example.moviesapp.di

import android.util.Log
import com.example.moviesapp.data.MoviesApi
import com.example.moviesapp.domain.repository.MoviesRepository
import com.example.moviesapp.util.Constants
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.StringReader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
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
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(client: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(newsApi: MoviesApi): MoviesRepository =
        MoviesRepository(newsApi)
}

