package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.*
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station

@Dao
interface StationDao {
    @Query("SELECT * FROM fav_station_table")
    suspend fun getAllStation(): List<Station>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStation(stationList: List<Station?>?)

    @Update
    suspend fun updateStation(station: Station)

    @Query("SELECT * FROM fav_station_table WHERE isFav =:isFav")
    suspend fun getAllFavSpaceStation(isFav: Boolean = true): List<Station>

    @Query("DELETE FROM fav_spacecraft_table")
    suspend fun removeFavSpaceStation()
}