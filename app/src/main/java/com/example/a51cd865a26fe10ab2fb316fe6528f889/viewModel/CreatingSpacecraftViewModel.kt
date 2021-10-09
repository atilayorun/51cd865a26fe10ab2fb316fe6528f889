package com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel

import androidx.lifecycle.ViewModel
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft

class CreatingSpacecraftViewModel : ViewModel() {
    private lateinit var databaseSpace: SpaceStationDatabase

    fun setDb(db: SpaceStationDatabase) {
        this.databaseSpace = db
    }

    fun addSpacecraft(spaceCraft:Spacecraft){
        databaseSpace.spaceCraftDao().insertSpacecraft(spaceCraft)
    }

    fun getSpacecraft():Spacecraft{
        return databaseSpace.spaceCraftDao().getSpacecraft()
    }
}