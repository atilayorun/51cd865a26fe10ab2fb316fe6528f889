package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.*
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft

@Dao
interface SpacecraftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpacecraft(spacecraft: Spacecraft?)

    @Query("SELECT * FROM fav_spacecraft_table")
    suspend fun getSpacecraft(): Spacecraft

    @Query("DELETE FROM fav_spacecraft_table")
    suspend fun removeSpacecraft()

    @Update
    suspend fun updateSpacecraft(spacecraft: Spacecraft)
}