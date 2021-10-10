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

class CreatingSpacecraftViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val favSpaceStationListLiveData = MutableLiveData<List<Station>>()
    val spaceCraftLiveData = MutableLiveData<Spacecraft>()
    val spaceStationListLiveData = MutableLiveData<List<Station>>()
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
            favSpaceStationListLiveData.postValue(databaseSpace.favSpaceStationDao().getAllFavSpaceStation())
        }
    }

    fun addAllStation() {
        scope.launch {
            databaseSpace.stationDao().insertAllStation(spaceStationListLiveData.value)
        }
    }

    fun getAllStationFromAPI() {
        val call = ApiClient.instance.getStationList()
        call.enqueue(object : Callback<List<Station>> {
            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                if (response.code() == 200) {
                    if (response.isSuccessful) {
                        spaceStationListLiveData.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                val msg = t.message
                val msg2 = t.localizedMessage
                println("hata")
            }
        })
    }
}