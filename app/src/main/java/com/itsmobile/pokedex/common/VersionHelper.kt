package com.itsmobile.pokedex.common

import com.itsmobile.pokedex.data.model.Version

/**
 * VersionHelper
 * This class help the conversion of the version in different type of char
 */

class VersionHelper {
    companion object {

        val versions = arrayListOf(
            Version(
                "RED, BLUE",
                "I",
                "https://pokeapi.co/api/v2/pokedex/2"
            ),
            Version(
                "GOLD, SILVER",
                "II",
                "https://pokeapi.co/api/v2/pokedex/3"
            ),
            Version(
                "RUBY, SAPPHIRE",
                "III",
                "https://pokeapi.co/api/v2/pokedex/4"
            ),
            Version(
                "DIAMOND, PEARL",
                "IV",
                "https://pokeapi.co/api/v2/pokedex/5"
            ),
            Version(
                "WHITE, BLACK",
                "V",
                "https://pokeapi.co/api/v2/pokedex/8"
            ),
            Version(
                "X, Y",
                "VI",
                "https://pokeapi.co/api/v2/pokedex/12"
            ),
            Version(
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