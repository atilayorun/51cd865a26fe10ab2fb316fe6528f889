package com.example.a51cd865a26fe10ab2fb316fe6528f889.repository

import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpacecraftDao
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import javax.inject.Inject

class SpacecraftRepository @Inject constructor(private val spacecraftDao: SpacecraftDao) {
    suspend fun insertSpacecraft(spacecraft: Spacecraft?) = spacecraftDao.insertSpacecraft(spacecraft)
    suspend fun updateSpacecraft(spacecraft: Spacecraft) = spacecraftDao.updateSpacecraft(spacecraft)
    suspend fun getSpacecraft():Spacecraft = spacecraftDao.getSpacecraft()
    suspend fun removeSpaceCraft() = spacecraftDao.removeSpacecraft()
}