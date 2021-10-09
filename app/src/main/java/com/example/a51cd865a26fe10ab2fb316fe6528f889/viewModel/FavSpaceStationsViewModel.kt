package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station

class FavSpaceStationsViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val favSpaceStationList = MutableLiveData<List<Station>>()

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun getAllFavStation(){
        favSpaceStationList.value = databaseSpace.favSpaceStationDao().getAllFavSpaceStation()
    }

    fun updateStation(station: Station){
        databaseSpace.stationDao().updateStation(station)
    }
}