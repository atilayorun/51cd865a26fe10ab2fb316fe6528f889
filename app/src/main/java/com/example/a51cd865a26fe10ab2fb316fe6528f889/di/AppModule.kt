package com.example.a51cd865a26fe10ab2fb316fe6528f889.di

import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiHelper
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiHelperImpl
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}