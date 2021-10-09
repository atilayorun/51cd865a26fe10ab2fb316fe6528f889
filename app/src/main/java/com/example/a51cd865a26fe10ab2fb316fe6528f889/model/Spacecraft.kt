package com.example.a51cd865a26fe10ab2fb316fe6528f889.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_spacecraft_table")
data class Spacecraft(
    @PrimaryKey val name:String,
    val speed: Int,
    val materialCapacity: Int,
    val durability: Int,
    val spaceSuitCount:Int,
    val universalSpaceTime:Int,
    val enduranceTime:Int,
    val damageCapacity: Int = 100,
)