package com.example.a51cd865a26fe10ab2fb316fe6528f889.repository

import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiHelper
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getStationList() = apiHelper.getStationList()
}