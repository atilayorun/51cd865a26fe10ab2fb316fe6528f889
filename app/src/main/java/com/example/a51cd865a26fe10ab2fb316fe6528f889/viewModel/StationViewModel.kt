package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.api.ApiClient
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StationViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val spaceStationList = MutableLiveData<List<Station>>()

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun getAllStation(){
        spaceStationList.value = databaseSpace.stationDao().getAllStation()
    }

    fun addStation(){
        databaseSpace.stationDao().insertAllStation(spaceStationList.value)
    }

    fun getSpacecraft(): Spacecraft {
        return databaseSpace.spaceCraftDao().getSpacecraft()
    }

    fun updateStation(station: Station){
        databaseSpace.stationDao().updateStation(station)
    }

    fun getAllStationFromAPI() {
        val call = ApiClient.instance.getStationList()
        call.enqueue(object : Callback<List<Station>> {
            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                if (response.code() == 200) {
                    if (response.isSuccessful) {
                        spaceStationList.value = response.body()
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

