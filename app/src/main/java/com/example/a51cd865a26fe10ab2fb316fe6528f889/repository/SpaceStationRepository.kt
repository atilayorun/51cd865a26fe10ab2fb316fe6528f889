package com.example.a51cd865a26fe10ab2fb316fe6528f889.repository

import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDao
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import javax.inject.Inject

class SpaceStationRepository @Inject constructor(private val spaceStationDao: SpaceStationDao) {
    suspend fun getAllStation():List<SpaceStation> = spaceStationDao.getAllStation()
    suspend fun insertAllStation(spaceStationList: List<SpaceStation?>?) = spaceStationDao.insertAllStation(spaceStationList)
    suspend fun updateStation(spaceStation:SpaceStation) = spaceStationDao.updateStation(spaceStation)
    suspend fun getAllFavSpaceStation() = spaceStationDao.getAllFavSpaceStation()
    suspend fun removeFavSpaceStation() = spaceStationDao.removeFavSpaceStation()
}