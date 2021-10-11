package com.example.a51cd865a26fe10ab2fb316fe6528f889.repository

import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.StationDao
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import javax.inject.Inject

class SpaceStationRepository @Inject constructor(private val stationDao: StationDao) {
    suspend fun getAllStation():List<Station> = stationDao.getAllStation()
    suspend fun insertAllStation(stationList: List<Station?>?) = stationDao.insertAllStation(stationList)
    suspend fun updateStation(station:Station) = stationDao.updateStation(station)
    suspend fun getAllFavSpaceStation() = stationDao.getAllFavSpaceStation()
    suspend fun removeFavSpaceStation() = stationDao.removeFavSpaceStation()
}