package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station

@Dao
interface FavSpaceStationDao {
    @Query("SELECT * FROM fav_station_table WHERE isFav =:isFav")
    fun getAllFavSpaceStation(isFav: Boolean = true): List<Station>

    @Update
    fun updateStation(station: Station)

    @Query("DELETE FROM fav_spacecraft_table")
    fun removeFavSpaceStation()
}