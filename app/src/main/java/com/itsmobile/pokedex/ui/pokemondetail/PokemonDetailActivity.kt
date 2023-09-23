package com.itsmobile.pokedex.ui.pokemondetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.databinding.ActivityPokemonDetailBinding
import com.itsmobile.pokedex.fragment.*
import com.itsmobile.pokedex.model.evolution.Evolution
import com.itsmobile.pokedex.viewmodels.EvolutionViewModel
import com.itsmobile.pokedex.model.evolution.EvolvesTo
import com.itsmobile.pokedex.viewmodels.LocationViewModel
import com.itsmobile.pokedex.model.location.Locations
import com.itsmobile.pokedex.model.pokemon.Pokemon
import com.itsmobile.pokedex.viewmodels.PokemonDetailViewModel

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    private val viewModel : PokemonDetailViewModel by viewModels()
    private val locationsViewModel : LocationViewModel by viewModels()
    private val evolutionViewModel : EvolutionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentView, LoadingFragment.newInstance())
            .commit()

        // getPokemonSpecies(intent?.getStringExtra("url") ?: "not found")

        viewModel.getPokemonSpecies(this, intent?.getStringExtra("url") ?: "not found")

        viewModel.pokemon.observe(this){
            if(it != null){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentView, PokemonDetailFragment.newInstance())
                    .commit()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.info.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentView, PokemonDetailFragment.newInstance())
                .commit()
        }

        binding.evolution.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            if(viewModel.evolution.value == null){
                viewModel.getEvolution(this)
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentView, LoadingFragment.newInstance())
                    .commit()
            }

            viewModel.evolution.observe(this){
                if(it != null){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonEvolutionFragment.newInstance())
                        .commit()
                }
            }
        }

        binding.location.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            if(viewModel.locations.value == null){
                viewModel.getLocation(this)
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentView, LoadingFragment.newInstance())
                    .commit()
            }

            viewModel.locations.observe(this){
                if(it != null){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonLocationFragment.newInstance())
                        .commit()
                }
            }
        }

        binding.moves.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentView, PokemonMovesFragment.newInstance())
                .commit()
        }
    }
    private fun deselectAll(){
        binding.moves.alpha = 0.7F
        binding.info.alpha = 0.7F
        binding.evolution.alpha = 0.7F
        binding.location.alpha = 0.7F
    }
}