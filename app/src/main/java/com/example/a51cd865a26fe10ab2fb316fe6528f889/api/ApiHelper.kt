package com.example.a51cd865a26fe10ab2fb316fe6528f889.api

import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import retrofit2.Response

interface ApiHelper {
    suspend fun getStationList(): Response<List<Station>>
}