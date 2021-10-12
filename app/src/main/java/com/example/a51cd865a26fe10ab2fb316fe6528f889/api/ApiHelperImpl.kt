package com.example.a51cd865a26fe10ab2fb316fe6528f889.api

import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{
    override suspend fun getStationList(): Response<List<SpaceStation>> = apiService.getStationList()
}