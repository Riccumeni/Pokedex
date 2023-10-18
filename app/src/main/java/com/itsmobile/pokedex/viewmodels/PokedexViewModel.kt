package com.itsmobile.pokedex.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.model.Response
import com.itsmobile.pokedex.model.pokemon.PokemonEntry
import com.itsmobile.pokedex.model.pokemon.PokemonItem
import com.itsmobile.pokedex.model.pokemon.PokemonSpecies
import com.itsmobile.pokedex.ui.adapters.PokemonAdapter

class PokedexViewModel: ViewModel() {

    var pokedexList = MutableLiveData<ArrayList<PokemonEntry>>()
    var pokedexListFiltered = MutableLiveData<ArrayList<PokemonEntry>>()
    var version = MutableLiveData<String>()
    var response = MutableLiveData<Response>()

    fun getPokemonListFilteredByGen(gen:String, context: Context) {
        val queue = Volley.newRequestQueue(context)
        val jsonReq = JsonObjectRequest(
            Request.Method.GET, gen, null,
            {response ->
                val poke = Gson().fromJson(response.toString(), PokemonItem::class.java)
                val pokemonEntries = ArrayList<PokemonEntry>()
                for (pokemonEntry in poke.pokemon_entries) {
                    pokemonEntries.add(PokemonEntry(pokemonEntry.entry_number, PokemonSpecies(pokemonEntry.pokemon_species.name,pokemonEntry.pokemon_species.url)))
                }

                this.response.value = Response(status = 200, data = pokemonEntries)

                pokedexListFiltered.value = this.response.value!!.data as ArrayList<PokemonEntry>
            },
            {
                this.response.value = Response(status = 404, data = null)
            }
        )
        queue.add(jsonReq)
    }

    fun getVersion(context: Context){
        val sharedPref = context.getSharedPreferences("version", Context.MODE_PRIVATE)
        version.value = sharedPref.getString("version", "")
    }

    fun getPokemonListFilteredOnText(text:String){
        pokedexListFiltered.value = ArrayList()
        (response.value?.data as ArrayList<PokemonEntry>).forEach { pokemon ->
            val regex = Regex("^${text}.*")
            if(regex.matches(pokemon.pokemon_species.name)){
                pokedexListFiltered.value?.add(pokemon)

            }
        }
    }
}