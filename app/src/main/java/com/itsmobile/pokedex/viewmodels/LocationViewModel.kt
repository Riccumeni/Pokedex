package com.itsmobile.pokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itsmobile.pokedex.model.location.LocationsItem

class LocationViewModel : ViewModel() {
    val locations = MutableLiveData<ArrayList<LocationsItem>>()
}