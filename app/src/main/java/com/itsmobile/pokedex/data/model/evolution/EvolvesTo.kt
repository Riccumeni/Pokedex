package com.itsmobile.pokedex.data.model.evolution

data class EvolvesTo(
    val evolution_details: List<com.itsmobile.pokedex.data.model.evolution.EvolutionDetail>,
    val evolves_to: List<com.itsmobile.pokedex.data.model.evolution.EvolvesTo>,
    val is_baby: Boolean,
    val species: com.itsmobile.pokedex.data.model.evolution.SpeciesX
)