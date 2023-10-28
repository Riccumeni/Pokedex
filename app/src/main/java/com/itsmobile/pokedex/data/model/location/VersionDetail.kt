package com.itsmobile.pokedex.data.model.location

data class VersionDetail(
    val encounter_details: List<com.itsmobile.pokedex.data.model.location.EncounterDetail>,
    val max_chance: Int,
    val version: Version
)