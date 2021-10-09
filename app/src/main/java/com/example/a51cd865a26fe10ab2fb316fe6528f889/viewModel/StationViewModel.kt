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
    val spacecraft = MutableLiveData<Spacecraft>()
    val spaceStationList = MutableLiveData<List<Station>>()
    private val scope = CoroutineScope(Dispatchers.IO)
    var asdf = 1

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }


    fun addAllStation() {
        scope.launch {
            databaseSpace.stationDao().insertAllStation(spaceStationList.value)
        }
    }

    fun getAllStation() {
        scope.launch {
            spaceStationList.postValue(databaseSpace.stationDao().getAllStation())
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

    private fun updateSpaceCraft(spacecraft: Spacecraft) {
        scope.launch {
            databaseSpace.spaceCraftDao().updateSpacecraft(spacecraft)
        }
    }

    fun btnTravelSetOnClick(station: Station) {
        spacecraft.value!!.currentPositionName = station.name
        spacecraft.value!!.universalSpaceTime -= Util.distanceFormula(station.coordinateX,spacecraft.value!!.coordinateX,station.coordinateY,spacecraft.value!!.coordinateY)
        spacecraft.value!!.coordinateX = station.coordinateX
        spacecraft.value!!.coordinateY = station.coordinateY
        if (spacecraft.value!!.spaceSuitCount >= station.need) {
            spacecraft.value!!.spaceSuitCount -= station.need
            station.need = 0
            station.stock = station.capacity
        } else {
            spacecraft.value!!.spaceSuitCount = 0
            station.need -= spacecraft.value!!.spaceSuitCount
            station.stock += spacecraft.value!!.spaceSuitCount
        }
        scope.launch {
            databaseSpace.stationDao().updateStation(station)
            databaseSpace.spaceCraftDao().updateSpacecraft(spacecraft.value!!)
            spaceStationList.postValue(databaseSpace.stationDao().getAllStation())
            spacecraft.postValue(databaseSpace.spaceCraftDao().getSpacecraft())
        }
    }
}

