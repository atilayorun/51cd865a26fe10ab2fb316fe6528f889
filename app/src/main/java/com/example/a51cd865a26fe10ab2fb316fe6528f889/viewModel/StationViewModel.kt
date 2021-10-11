package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpaceStationRepository
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpacecraftRepository
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationViewModel @ViewModelInject constructor(private val spaceStationRepository: SpaceStationRepository, private val spaceCraftRepository: SpacecraftRepository) : ViewModel() {
    val spacecraftLiveData = MutableLiveData<Spacecraft>()
    val spaceStationListLiveData = MutableLiveData<List<Station>>()
    val canGo = MutableLiveData<Boolean>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getAllData() {
        scope.launch {
            spacecraftLiveData.postValue(spaceCraftRepository.getSpacecraft())
            spaceStationListLiveData.postValue(spaceStationRepository.getAllStation())
        }
    }

    fun updateStation(station: Station) {
        scope.launch {
            spaceStationRepository.updateStation(station)
        }
    }

    fun btnTravelSetOnClick(station: Station) {
        val distance = Util.distanceFormula(
            station.coordinateX,
            spacecraftLiveData.value!!.coordinateX,
            station.coordinateY,
            spacecraftLiveData.value!!.coordinateY
        )
        if (spacecraftLiveData.value!!.universalSpaceTime < distance) {
            canGo.postValue(false)
        } else if (spacecraftLiveData.value!!.spaceSuitCount == 0 || spacecraftLiveData.value!!.spaceSuitCount < station.need) {
            canGo.postValue(false)
        }
        else if (spacecraftLiveData.value!!.enduranceTime < station.distanceToSpacecraft*1000) {
            canGo.postValue(false)
        }
        else {
            spacecraftLiveData.value!!.currentPositionName = station.name
            spacecraftLiveData.value!!.universalSpaceTime -= distance
            spacecraftLiveData.value!!.coordinateX = station.coordinateX
            spacecraftLiveData.value!!.coordinateY = station.coordinateY
            spacecraftLiveData.value!!.enduranceTime -= distance * 1000
            spacecraftLiveData.value!!.damageCapacity -= 10
            spacecraftLiveData.value!!.spaceSuitCount -= station.need
            station.need = 0
            station.stock = station.capacity

            scope.launch {
                spaceStationRepository.updateStation(station)
                spaceCraftRepository.updateSpacecraft(spacecraftLiveData.value!!)
                spacecraftLiveData.postValue(spaceCraftRepository.getSpacecraft())
                spaceStationListLiveData.postValue(spaceStationRepository.getAllStation())
            }
        }
    }
}

