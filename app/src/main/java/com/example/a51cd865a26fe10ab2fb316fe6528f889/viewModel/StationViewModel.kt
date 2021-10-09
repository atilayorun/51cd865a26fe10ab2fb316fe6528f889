package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiClient
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StationViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val spacecraft = MutableLiveData<Spacecraft>()
    val spaceStationList = MutableLiveData<List<Station>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun getAllStation() {
        scope.launch {
            spaceStationList.postValue(databaseSpace.stationDao().getAllStation())
        }
    }

    fun addAllStation() {
        scope.launch {
            databaseSpace.stationDao().insertAllStation(spaceStationList.value)
        }
    }

    fun getSpacecraft() {
        scope.launch {
            spacecraft.postValue(databaseSpace.spaceCraftDao().getSpacecraft())
        }
    }

    fun updateStation(station: Station) {
        scope.launch {
            databaseSpace.stationDao().updateStation(station)
        }
    }
}

