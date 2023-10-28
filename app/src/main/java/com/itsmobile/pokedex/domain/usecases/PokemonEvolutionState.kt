package com.itsmobile.pokedex.domain.usecases

open class PokemonEvolutionState()

class PokemonEvolutionInitial: PokemonEvolutionState()

class PokemonEvolutionLoading: PokemonEvolutionState()

class PokemonEvolutionError: PokemonEvolutionState()

class PokemonEvolutionSuccess(val evolutions: ArrayList<Map<String, String>>) : PokemonEvolutionState()