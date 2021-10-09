package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.*
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station

@Dao
interface StationDao {
    @Query("SELECT * FROM fav_station_table")
    fun getAllStation(): List<Station>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStation(station: List<Station?>?)

    @Update
    fun updateStation(station: Station)
}