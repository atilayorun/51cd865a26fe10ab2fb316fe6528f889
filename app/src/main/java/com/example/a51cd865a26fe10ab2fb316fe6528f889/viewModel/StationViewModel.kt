package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val spacecraftLiveData = MutableLiveData<Spacecraft>()
    val spaceStationListLiveData = MutableLiveData<List<Station>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun getAllData(){
        scope.launch {
            spacecraftLiveData.postValue(databaseSpace.spaceCraftDao().getSpacecraft())
            spaceStationListLiveData.postValue(databaseSpace.stationDao().getAllStation())
        }
    }

    fun updateStation(station: Station) {
        scope.launch {
            databaseSpace.stationDao().updateStation(station)
        }
    }

    private fun updateSpaceCraft(spacecraft: Spacecraft) {
        scope.launch {
            databaseSpace.spaceCraftDao().updateSpacecraft(spacecraft)
        }
    }

    fun btnTravelSetOnClick(station: Station) {
        val distance = Util.distanceFormula(station.coordinateX,spacecraftLiveData.value!!.coordinateX,station.coordinateY,spacecraftLiveData.value!!.coordinateY)
        spacecraftLiveData.value!!.currentPositionName = station.name
        spacecraftLiveData.value!!.universalSpaceTime -= distance
        spacecraftLiveData.value!!.coordinateX = station.coordinateX
        spacecraftLiveData.value!!.coordinateY = station.coordinateY

        spacecraftLiveData.value!!.enduranceTime -= distance * 1000
        spacecraftLiveData.value!!.damageCapacity -= 10

        if (spacecraftLiveData.value!!.spaceSuitCount >= station.need) {
            spacecraftLiveData.value!!.spaceSuitCount -= station.need
            station.need = 0
            station.stock = station.capacity
        } else {
            spacecraftLiveData.value!!.spaceSuitCount = 0
            station.need -= spacecraftLiveData.value!!.spaceSuitCount
            station.stock += spacecraftLiveData.value!!.spaceSuitCount
        }
        scope.launch {
            databaseSpace.stationDao().updateStation(station)
            databaseSpace.spaceCraftDao().updateSpacecraft(spacecraftLiveData.value!!)
            spaceStationListLiveData.postValue(databaseSpace.stationDao().getAllStation())
            spacecraftLiveData.postValue(databaseSpace.spaceCraftDao().getSpacecraft())
        }
    }
}

