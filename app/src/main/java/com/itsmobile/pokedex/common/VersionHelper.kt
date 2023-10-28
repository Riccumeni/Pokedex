package com.itsmobile.pokedex.common

import com.itsmobile.pokedex.data.model.Version

class VersionHelper {
    companion object {

        val versions = arrayListOf<com.itsmobile.pokedex.data.model.Version>(
            com.itsmobile.pokedex.data.model.Version(
                "RED, BLUE",
                "I",
                "https://pokeapi.co/api/v2/pokedex/2"
            ),
            com.itsmobile.pokedex.data.model.Version(
                "GOLD, SILVER",
                "II",
                "https://pokeapi.co/api/v2/pokedex/3"
            ),
            com.itsmobile.pokedex.data.model.Version(
                "RUBY, SAPPHIRE",
                "III",
                "https://pokeapi.co/api/v2/pokedex/4"
            ),
            com.itsmobile.pokedex.data.model.Version(
                "DIAMOND, PEARL",
                "IV",
                "https://pokeapi.co/api/v2/pokedex/5"
            ),
            com.itsmobile.pokedex.data.model.Version(
                "WHITE, BLACK",
                "V",
                "https://pokeapi.co/api/v2/pokedex/8"
            ),
            com.itsmobile.pokedex.data.model.Version(
                "X, Y",
                "VI",
                "https://pokeapi.co/api/v2/pokedex/12"
            ),
            com.itsmobile.pokedex.data.model.Version(
                "SUN, MOON",
                "VII",
                "https://pokeapi.co/api/v2/pokedex/16"
            )
        )
        fun getRomanVersionByUrl(url: String) : String{
            var numberVersion = ""

            versions.forEach { version ->
                if(version.url.equals(url)){
                    numberVersion = version.number
                }
            }

            return numberVersion
        }
    }
}