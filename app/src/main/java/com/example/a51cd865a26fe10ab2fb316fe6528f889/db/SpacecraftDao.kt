package com.example.a51cd865a26fe10ab2fb316fe6528f889.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft

@Dao
interface SpacecraftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpacecraft(spacecraft: Spacecraft?)

    @Query("SELECT * FROM fav_spacecraft_table")
    fun getSpacecraft(): Spacecraft
}