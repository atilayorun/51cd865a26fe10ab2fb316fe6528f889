package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavSpaceStationsViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase
    val favSpaceStationListLiveData = MutableLiveData<List<Station>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun getAllFavStation() {
        scope.launch {
            favSpaceStationListLiveData.postValue(
                databaseSpace.favSpaceStationDao().getAllFavSpaceStation()
            )
        }
    }

    fun updateAndGetAllFavStation(station: Station) {
        scope.launch {
            databaseSpace.stationDao().updateStation(station)
            favSpaceStationListLiveData.postValue(
                databaseSpace.favSpaceStationDao().getAllFavSpaceStation()
            )
        }
    }
}