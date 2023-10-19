package com.itsmobile.pokedex.model.location

open class PokemonLocationState()

class PokemonLocationInitial: PokemonLocationState()

class PokemonLocationLoading: PokemonLocationState()

class PokemonLocationError: PokemonLocationState()

class PokemonLocationSuccess(val locations: ArrayList<LocationsItem>) : PokemonLocationState()
