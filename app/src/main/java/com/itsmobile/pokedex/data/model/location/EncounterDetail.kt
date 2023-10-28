package com.itsmobile.pokedex.data.model.location

data class EncounterDetail(
    val chance: Int,
    val condition_values: List<com.itsmobile.pokedex.data.model.location.ConditionValue>,
    val max_level: Int,
    val method: com.itsmobile.pokedex.data.model.location.Method,
    val min_level: Int
)