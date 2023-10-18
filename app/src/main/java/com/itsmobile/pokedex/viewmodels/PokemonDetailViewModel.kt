package com.itsmobile.pokedex.viewmodels

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.model.Response
import com.itsmobile.pokedex.model.ability.EffectEntries
import com.itsmobile.pokedex.model.evolution.Evolution
import com.itsmobile.pokedex.model.evolution.EvolvesTo
import com.itsmobile.pokedex.model.location.Locations
import com.itsmobile.pokedex.model.location.LocationsItem
import com.itsmobile.pokedex.model.pokemon.Pokemon

class PokemonDetailViewModel : ViewModel(){
    var pokemon = MutableLiveData<Pokemon>()
    var evolutionUrl = MutableLiveData<String>()
    var evolution = MutableLiveData<ArrayList<Map<String, String>>>()
    var locationUrl = MutableLiveData<String>()
    var locations = MutableLiveData<ArrayList<LocationsItem>>()
    var response = MutableLiveData<Response>()

    fun getPokemonSpecies(context: Context, url: String){
        val queue = Volley.newRequestQueue(context)

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val urlPokemon = response.getJSONArray("varieties").getJSONObject(0).getJSONObject("pokemon").getString("url").toString()

                getPokemonDetail(context, urlPokemon)

                evolutionUrl.value = response.getJSONObject("evolution_chain").getString("url")

                this.response.value = Response(status = 200, data = null)
            },
            { error ->
                Log.d("errore", error.message.toString())
                this.response.value = Response(status = 404, data = null)
            }
        )
        queue.add(jsonRequest)
    }

    private fun getPokemonDetail(context: Context, url: String){
        val queue = Volley.newRequestQueue(context)

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val pokemon = Gson().fromJson(response.toString(), Pokemon::class.java)
                val sharedPref = context.getSharedPreferences("version", Context.MODE_PRIVATE)
                var version = sharedPref.getString("version", "")

                when(version){
                    "https://pokeapi.co/api/v2/pokedex/2"-> version = "red-blue"
                    "https://pokeapi.co/api/v2/pokedex/3" -> version = "gold-silver"
                    "https://pokeapi.co/api/v2/pokedex/4" -> version = "ruby-sapphire"
                    "https://pokeapi.co/api/v2/pokedex/5" -> version = "platinum"
                    "https://pokeapi.co/api/v2/pokedex/8" -> version = "black-white"
                    "https://pokeapi.co/api/v2/pokedex/12" -> version = "x-y"
                    "https://pokeapi.co/api/v2/pokedex/16" -> version = "sun-moon"
                }

                pokemon.movesFilteredByLevel = pokemon.getFilteredMoves("level-up", version ?: "red-blue")
                pokemon.movesFilteredByMachine = pokemon.getFilteredMoves("machine", version ?: "red-blue")
                pokemon.movesFilteredByEggs = pokemon.getFilteredMoves("egg", version ?: "red-blue")

                pokemon.movesFilteredByMachine.forEach {
                    it.move.setMachine(context, "machine", version ?: "")
                }

                pokemon.movesFilteredByLevel.forEach {
                    it.move.setMachine(context, "level-up", version ?: "")
                }

                pokemon.movesFilteredByEggs.forEach {
                    it.move.setMachine(context, "Egg", version ?: "")
                }

                this.pokemon.value = pokemon
                locationUrl.value = response.getString("location_area_encounters")

                getAbilityDescription(context)

                this.response.value = Response(status = 200, data = null)
            },
            { error ->
                Log.d("errore", error.message.toString())
                this.response.value = Response(status = 404, data = null)
            }
        )
        queue.add(jsonRequest)
    }

    private fun getAbilityDescription(context: Context){
        pokemon.value?.abilities?.forEach { ability ->
            val queue = Volley.newRequestQueue(context)

            val jsonRequest = JsonObjectRequest(
                Request.Method.GET,
                ability.ability.url,
                null,
                { response ->
                    val ab = Gson().fromJson(response.toString(), EffectEntries::class.java)
                    for (effect in ab.effect_entries){
                        if(effect.language.name == "en"){
                            ability.ability.description = effect.effect
                        }
                    }

                    this.response.value = Response(status = 200, data = null)
                },
                { error ->
                    Log.d("errore", error.message.toString())
                    this.response.value = Response(status = 404, data = null)
                }
            )
            queue.add(jsonRequest)
        }
    }

    fun getEvolution(context: Context){
        val queue = Volley.newRequestQueue(context)

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            evolutionUrl.value,
            null,
            { response ->
                val evolutions = Gson().fromJson(response.toString(), Evolution::class.java)

                var evo = ArrayList<Map<String, String>>()
                val regex = Regex("""\d+(?=/?$)""").find(evolutions.chain.species.url)?.value.toString()
                evo.add(mapOf("url" to "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${regex}.png"))

                evo = getEvolutionsRecursive(evolutions.chain.evolves_to, evo)

                evolution.value = evo
                this.response.value = Response(status = 200, data = null)
            },
            { error ->
                Log.d("errore", error.message.toString())
                this.response.value = Response(status = 404, data = null)
            }
        )
        queue.add(jsonRequest)
    }

    private fun getEvolutionsRecursive(evolution: List<EvolvesTo>, evo: ArrayList<Map<String, String>>) : ArrayList<Map<String, String>>{
        if(evolution.isEmpty()){
            return evo
        }
        var lv = evolution[0].evolution_details[0].min_level.toString()
        if (lv == "0"){
            lv = evolution[0].evolution_details[0].trigger.name
        }else{
            lv = "lv. ${lv}"
        }
        val regex = Regex("""\d+(?=/?$)""").find(evolution[0].species.url)?.value.toString()
        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${regex}.png"

        evo.add(mapOf("lv" to lv, "url" to url))

        return getEvolutionsRecursive(evolution[0].evolves_to, evo)
    }

    fun getLocation(context: Context){
        val queue = Volley.newRequestQueue(context)

        val jsonRequest = JsonArrayRequest(
            Request.Method.GET,
            locationUrl.value,
            null,
            { response ->

                val sharedPref = context.getSharedPreferences("version", Context.MODE_PRIVATE)
                var version = sharedPref.getString("version", "")

                when(version){
                    "https://pokeapi.co/api/v2/pokedex/2" -> version = "red-blue"
                    "https://pokeapi.co/api/v2/pokedex/3" -> version = "gold-silver"
                    "https://pokeapi.co/api/v2/pokedex/4" -> version = "ruby-sapphire"
                    "https://pokeapi.co/api/v2/pokedex/5" -> version = "diamond-pearl"
                    "https://pokeapi.co/api/v2/pokedex/8" -> version = "black-white"
                    "https://pokeapi.co/api/v2/pokedex/12" -> version = "x-y"
                    "https://pokeapi.co/api/v2/pokedex/16" -> version = "sun-moon"
                }

                val locations = version?.let { ver ->
                    Gson().fromJson(response.toString(), Locations::class.java).getLocationFilteredByVersion(ver)
                }

                this.locations.value = locations

                this.response.value = Response(status = 200, data = null)

            },
            { error ->
                Log.e("errore", error.message.toString())
                this.response.value = Response(status = 404, data = null)
            }
        )
        queue.add(jsonRequest)
    }
}