package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station

@Database(entities = [Station::class,Spacecraft::class], version = 5, exportSchema = false)
abstract class SpaceStationDatabase : RoomDatabase() {
     abstract fun stationDao(): StationDao
     abstract fun favSpaceStationDao(): FavSpaceStationDao
     abstract fun spaceCraftDao(): SpacecraftDao

    companion object {
        private var instance: SpaceStationDatabase? = null

        fun getStationDatabase(context: Context): SpaceStationDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    SpaceStationDatabase::class.java,
                    "station.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}