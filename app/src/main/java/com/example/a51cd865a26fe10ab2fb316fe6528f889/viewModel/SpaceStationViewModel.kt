package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpaceStationRepository
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpacecraftRepository
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpaceStationViewModel @ViewModelInject constructor(private val spaceStationRepository: SpaceStationRepository, private val spaceCraftRepository: SpacecraftRepository) : ViewModel() {
    val spacecraftLiveData = MutableLiveData<Spacecraft>()
    val spaceStationListLiveData = MutableLiveData<List<SpaceStation>>()
    val canGo = MutableLiveData<Boolean>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getAllData() {
        scope.launch {
            spacecraftLiveData.postValue(spaceCraftRepository.getSpacecraft())
            spaceStationListLiveData.postValue(spaceStationRepository.getAllStation())
        }
    }

    fun updateStation(spaceStation: SpaceStation) {
        scope.launch {
            spaceStationRepository.updateStation(spaceStation)
        }
    }

    fun btnTravelSetOnClick(spaceStation: SpaceStation) {
        val distance = Util.distanceFormula(
            spaceStation.coordinateX,
            spacecraftLiveData.value!!.coordinateX,
            spaceStation.coordinateY,
            spacecraftLiveData.value!!.coordinateY
        )
        if (spacecraftLiveData.value!!.universalSpaceTime < distance) {
            canGo.postValue(false)
        } else if (spacecraftLiveData.value!!.spaceSuitCount == 0 || spacecraftLiveData.value!!.spaceSuitCount < spaceStation.need) {
            canGo.postValue(false)
        }
        else if (spacecraftLiveData.value!!.enduranceTime < spaceStation.distanceToSpacecraft*1000) {
            canGo.postValue(false)
        }
        else {
            spacecraftLiveData.value!!.currentPositionName = spaceStation.name
            spacecraftLiveData.value!!.universalSpaceTime -= distance
            spacecraftLiveData.value!!.coordinateX = spaceStation.coordinateX
            spacecraftLiveData.value!!.coordinateY = spaceStation.coordinateY
            spacecraftLiveData.value!!.enduranceTime -= distance * 1000
            spacecraftLiveData.value!!.damageCapacity -= 10
            spacecraftLiveData.value!!.spaceSuitCount -= spaceStation.need
            spaceStation.need = 0
            spaceStation.stock = spaceStation.capacity

            scope.launch {
                spaceStationRepository.updateStation(spaceStation)
                spaceCraftRepository.updateSpacecraft(spacecraftLiveData.value!!)
                spacecraftLiveData.postValue(spaceCraftRepository.getSpacecraft())
                spaceStationListLiveData.postValue(spaceStationRepository.getAllStation())
            }
        }
    }
}

