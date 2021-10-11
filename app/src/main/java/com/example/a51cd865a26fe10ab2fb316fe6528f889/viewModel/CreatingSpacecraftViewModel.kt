package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.MainRepository
import kotlinx.coroutines.*

class CreatingSpacecraftViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val favSpaceStationListLiveData = MutableLiveData<List<Station>>()
    val spaceCraftLiveData = MutableLiveData<Spacecraft>()
    val spaceStationListLiveData = MutableLiveData<List<Station>>()
    val apiOnFailure = MutableLiveData<Boolean>()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun addSpacecraft(spaceCraft: Spacecraft) {
        scope.launch {
            databaseSpace.spaceCraftDao().insertSpacecraft(spaceCraft)
        }
    }


    fun removeSpacecraft() {
        scope.launch {
            databaseSpace.spaceCraftDao().removeSpacecraft()
        }
    }

    fun removeFavSpaceStation() {
        scope.launch {
            databaseSpace.favSpaceStationDao().removeFavSpaceStation()
        }
    }

    fun getFavSpaceStation() {
        scope.launch {
            favSpaceStationListLiveData.postValue(
                databaseSpace.favSpaceStationDao().getAllFavSpaceStation()
            )
        }
    }

    fun addAllStation() {
        scope.launch {
            databaseSpace.stationDao().insertAllStation(spaceStationListLiveData.value)
        }
    }

    fun getAllStationFromAPI() {
        scope.launch {
            mainRepository.getStationList().let {
                if (it.isSuccessful)
                    spaceStationListLiveData.postValue(it.body())
                else
                    apiOnFailure.postValue(true)

            }
        }
    }
}