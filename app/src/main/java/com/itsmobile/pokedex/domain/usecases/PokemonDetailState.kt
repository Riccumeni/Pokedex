package com.itsmobile.pokedex.domain.usecases

import com.itsmobile.pokedex.data.model.pokemon.Pokemon

open class PokemonDetailState()

class PokemonDetailInitial: PokemonDetailState()

class PokemonDetailLoading: PokemonDetailState()

class PokemonDetailError: PokemonDetailState()

class PokemonDetailSuccess(val pokemon: Pokemon) : PokemonDetailState()