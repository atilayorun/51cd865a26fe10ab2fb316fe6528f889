package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.RetrofitRepository
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpacecraftRepository
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpaceStationRepository
import kotlinx.coroutines.*

class CreatingSpacecraftViewModel @ViewModelInject constructor(private val retrofitRepository: RetrofitRepository, private val spaceStationRepository: SpaceStationRepository, private val spaceCraftRepository: SpacecraftRepository) :
    ViewModel() {
    val favSpaceStationListLiveData = MutableLiveData<List<SpaceStation>>()
    val spaceCraftLiveData = MutableLiveData<Spacecraft>()
    val spaceStationListLiveData = MutableLiveData<List<SpaceStation>>()
    val apiOnFailure = MutableLiveData<Boolean>()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun addSpacecraft(spaceCraft: Spacecraft) {
        scope.launch {
            spaceCraftRepository.insertSpacecraft(spaceCraft)
        }
    }


    fun removeSpacecraft() {
        scope.launch {
            spaceCraftRepository.removeSpaceCraft()
        }
    }

    fun removeFavSpaceStation() {
        scope.launch {
            spaceStationRepository.removeFavSpaceStation()
        }
    }

    fun getFavSpaceStation() {
        scope.launch {
            favSpaceStationListLiveData.postValue(
                spaceStationRepository.getAllFavSpaceStation()
            )
        }
    }

    fun addAllStation() {
        scope.launch {
            spaceStationRepository.insertAllStation(spaceStationListLiveData.value)
        }
    }

    fun getAllStationFromAPI() {
        scope.launch {
            retrofitRepository.getStationList().let {
                if (it.isSuccessful)
                    spaceStationListLiveData.postValue(it.body())
                else
                    apiOnFailure.postValue(true)
            }
        }
    }
}