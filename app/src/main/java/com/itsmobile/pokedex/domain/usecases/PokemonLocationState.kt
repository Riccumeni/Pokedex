package com.itsmobile.pokedex.domain.usecases

import com.itsmobile.pokedex.data.model.location.LocationsItem

open class PokemonLocationState()

class PokemonLocationInitial: PokemonLocationState()

class PokemonLocationLoading: PokemonLocationState()

class PokemonLocationError: PokemonLocationState()

class PokemonLocationSuccess(val locations: ArrayList<LocationsItem>) : PokemonLocationState()
