package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station

@Database(entities = [Station::class,Spacecraft::class], version = 5, exportSchema = false)
abstract class ProjectDatabase : RoomDatabase() {
     abstract fun stationDao(): StationDao
     abstract fun spaceCraftDao(): SpacecraftDao
}