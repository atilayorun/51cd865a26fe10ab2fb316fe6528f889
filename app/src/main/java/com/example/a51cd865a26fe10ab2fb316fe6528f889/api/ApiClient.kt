package com.example.a51cd865a26fe10ab2fb316fe6528f889.api

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


object ApiClient {
    private const val BASE_URL = "https://run.mocky.io/"

        fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    fun provideApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}