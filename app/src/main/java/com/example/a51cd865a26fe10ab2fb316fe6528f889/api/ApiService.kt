package com.example.a51cd865a26fe10ab2fb316fe6528f889.api

import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getStationList(): Response<List<SpaceStation>>
}