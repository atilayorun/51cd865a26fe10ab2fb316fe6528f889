package com.example.a51cd865a26fe10ab2fb316fe6528f889.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fav_station_table")
data class Station(
    @PrimaryKey @SerializedName("name") val name: String,
    @SerializedName("coordinateX") val coordinateX: Int,
    @SerializedName("coordinateY") val coordinateY: Int,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("need") val need: Int,
    var isFav: Boolean=false
)