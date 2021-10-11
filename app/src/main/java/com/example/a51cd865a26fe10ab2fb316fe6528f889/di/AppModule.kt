package com.example.a51cd865a26fe10ab2fb316fe6528f889.di

import android.content.Context
import androidx.room.Room
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiHelper
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiHelperImpl
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiService
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.ProjectDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpacecraftDao
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.StationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    fun provideProjectDatabase(@ApplicationContext appContext: Context): ProjectDatabase {
        return Room.databaseBuilder(
            appContext,
            ProjectDatabase::class.java,
            "station.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    fun provideSpaceStationDao(projectDatabase: ProjectDatabase):StationDao = projectDatabase.stationDao()

    @Provides
    fun provideSpacecraftDao(projectDatabase: ProjectDatabase):SpacecraftDao = projectDatabase.spaceCraftDao()
}