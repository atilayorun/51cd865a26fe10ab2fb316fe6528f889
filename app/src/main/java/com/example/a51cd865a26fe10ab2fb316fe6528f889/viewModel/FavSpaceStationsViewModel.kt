package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.repository.SpaceStationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavSpaceStationsViewModel @ViewModelInject constructor(private val spaceStationRepository: SpaceStationRepository) : ViewModel(){

    val favSpaceStationListLiveData = MutableLiveData<List<SpaceStation>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getAllFavStation() {
        scope.launch {
            favSpaceStationListLiveData.postValue(
                spaceStationRepository.getAllFavSpaceStation()
            )
        }
    }

    fun updateAndGetAllFavStation(spaceStation: SpaceStation) {
        scope.launch {
            spaceStationRepository.updateStation(spaceStation)
            favSpaceStationListLiveData.postValue(
                spaceStationRepository.getAllFavSpaceStation()
            )
        }
    }
}