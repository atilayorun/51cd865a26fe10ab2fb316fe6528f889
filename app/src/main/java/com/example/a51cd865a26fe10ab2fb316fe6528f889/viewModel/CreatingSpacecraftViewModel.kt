package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiClient
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import kotlinx.coroutines.*

class CreatingSpacecraftViewModel : ViewModel() {
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
            try {
                val response = ApiClient.instance.getStationList()
                if (response.isSuccessful && response.body() != null) {
                    spaceStationListLiveData.postValue(response.body())
                } else {
                    apiOnFailure.postValue(true)
                }
            } catch (e: Exception) {
                apiOnFailure.postValue(true)
                println("hata ! ${e.message}")
            }
        }
    }
}