package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpaceStationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavSpaceStationsViewModel @ViewModelInject constructor(private val spaceStationRepository: SpaceStationRepository) : ViewModel(){

    val favSpaceStationListLiveData = MutableLiveData<List<Station>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getAllFavStation() {
        scope.launch {
            favSpaceStationListLiveData.postValue(
                spaceStationRepository.getAllFavSpaceStation()
            )
        }
    }

    fun updateAndGetAllFavStation(station: Station) {
        scope.launch {
            spaceStationRepository.updateStation(station)
            favSpaceStationListLiveData.postValue(
                spaceStationRepository.getAllFavSpaceStation()
            )
        }
    }
}