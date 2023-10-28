package com.itsmobile.pokedex.data.model.evolution

data class Chain(
    val evolution_details: List<Any>,
    val evolves_to: List<com.itsmobile.pokedex.data.model.evolution.EvolvesTo>,
    val is_baby: Boolean,
    val species: com.itsmobile.pokedex.data.model.evolution.SpeciesX
)