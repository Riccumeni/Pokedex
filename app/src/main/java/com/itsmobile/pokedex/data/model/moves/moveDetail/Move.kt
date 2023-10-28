package com.itsmobile.pokedex.data.model.moves.moveDetail

data class Move(
    val accuracy: Int,
    val damage_class: DamageClass,
    val id: Int,
    val learned_by_pokemon: List<LearnedByPokemon>,
    val name: String,
    val power: Int,
    val pp: Int,
    val priority: Int,
    val target: Target,
    val type: Type
)