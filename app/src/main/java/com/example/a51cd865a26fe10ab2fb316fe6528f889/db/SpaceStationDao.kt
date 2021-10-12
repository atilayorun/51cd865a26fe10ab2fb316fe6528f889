package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.*
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation

@Dao
interface SpaceStationDao {
    @Query("SELECT * FROM fav_station_table")
    suspend fun getAllStation(): List<SpaceStation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStation(spaceStationList: List<SpaceStation?>?)

    @Update
    suspend fun updateStation(spaceStation: SpaceStation)

    @Query("SELECT * FROM fav_station_table WHERE isFav =:isFav")
    suspend fun getAllFavSpaceStation(isFav: Boolean = true): List<SpaceStation>

    @Query("DELETE FROM fav_spacecraft_table")
    suspend fun removeFavSpaceStation()
}