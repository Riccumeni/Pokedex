package com.itsmobile.pokedex.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itsmobile.pokedex.data.model.location.LocationsItem

class LocationViewModel : ViewModel() {
    val locations = MutableLiveData<ArrayList<LocationsItem>>()
}