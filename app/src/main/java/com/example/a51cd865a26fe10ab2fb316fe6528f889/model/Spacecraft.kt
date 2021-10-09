package com.example.a51cd865a26fe10ab2fb316fe6528f889.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_spacecraft_table")
data class Spacecraft(
    @PrimaryKey val name:String,
    var speed: Int,
    var materialCapacity: Int,
    var durability: Int,
    var spaceSuitCount:Int,
    var universalSpaceTime:Int,
    var enduranceTime:Int,
    var damageCapacity: Int = 100,
    var currentPositionName:String,
    var coordinateX:Double,
    var coordinateY:Double
)