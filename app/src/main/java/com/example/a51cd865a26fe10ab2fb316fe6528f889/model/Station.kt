package com.example.a51cd865a26fe10ab2fb316fe6528f889.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fav_station_table")
data class Station(
    @PrimaryKey var name: String,
    var coordinateX: Double,
    var coordinateY: Double,
    var capacity: Int,
    var stock: Int,
    var need: Int,
    var isFav: Boolean = false,
    var isActive: Boolean = false,
    var distanceToSpacecraft: Int
)