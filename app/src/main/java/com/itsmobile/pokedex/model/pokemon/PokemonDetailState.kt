package com.itsmobile.pokedex.model.pokemon

open class PokemonDetailState()

class PokemonDetailInitial: PokemonDetailState()

class PokemonDetailLoading: PokemonDetailState()

class PokemonDetailError: PokemonDetailState()

class PokemonDetailSuccess(val pokemon: Pokemon) : PokemonDetailState()