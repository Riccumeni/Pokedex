package com.itsmobile.pokedex.data.model.location

import com.google.gson.reflect.TypeToken

class Locations : ArrayList<com.itsmobile.pokedex.data.model.location.LocationsItem>(){

    fun getLocationFilteredByVersion(version: String) : ArrayList<com.itsmobile.pokedex.data.model.location.LocationsItem>{
        val locationsFiltered = ArrayList<com.itsmobile.pokedex.data.model.location.LocationsItem>()

        val versions = version.split("-")

        this.forEach{ location ->
            location.version_details.forEach {versionDetail ->
                if(versionDetail.version.name == versions[0] || versionDetail.version.name == versions[1]){
                    val version = ArrayList<com.itsmobile.pokedex.data.model.location.VersionDetail>()
                    version.add(versionDetail)
                    locationsFiltered.add(
                        com.itsmobile.pokedex.data.model.location.LocationsItem(
                            location.location_area,
                            version
                        )
                    )
                }
            }
        }

        return locationsFiltered
    }
}