package com.itsmobile.pokedex.model.evolution

import com.itsmobile.pokedex.model.location.LocationsItem
import com.itsmobile.pokedex.model.location.PokemonLocationState

open class PokemonEvolutionState()

class PokemonEvolutionInitial: PokemonEvolutionState()

class PokemonEvolutionLoading: PokemonEvolutionState()

class PokemonEvolutionError: PokemonEvolutionState()

class PokemonEvolutionSuccess(val evolutions: ArrayList<Map<String, String>>) : PokemonEvolutionState()