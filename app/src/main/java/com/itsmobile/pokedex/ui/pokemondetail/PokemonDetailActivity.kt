package com.itsmobile.pokedex.ui.pokemondetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.databinding.ActivityPokemonDetailBinding
import com.itsmobile.pokedex.model.evolution.PokemonEvolutionError
import com.itsmobile.pokedex.model.evolution.PokemonEvolutionInitial
import com.itsmobile.pokedex.model.evolution.PokemonEvolutionLoading
import com.itsmobile.pokedex.model.evolution.PokemonEvolutionSuccess
import com.itsmobile.pokedex.model.location.PokemonLocationError
import com.itsmobile.pokedex.model.location.PokemonLocationInitial
import com.itsmobile.pokedex.model.location.PokemonLocationLoading
import com.itsmobile.pokedex.model.location.PokemonLocationSuccess
import com.itsmobile.pokedex.model.pokemon.PokemonDetailError
import com.itsmobile.pokedex.model.pokemon.PokemonDetailInitial
import com.itsmobile.pokedex.model.pokemon.PokemonDetailLoading
import com.itsmobile.pokedex.model.pokemon.PokemonDetailSuccess
import com.itsmobile.pokedex.ui.ErrorFragment
import com.itsmobile.pokedex.ui.LoadingFragment
import com.itsmobile.pokedex.viewmodels.PokemonDetailViewModel

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    private val viewModel : PokemonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentView, LoadingFragment.newInstance())
            .commit()

        viewModel.response.observe(this){ response ->
            when(response){
                is PokemonDetailInitial -> {
                    viewModel.getPokemonSpecies(this, intent?.getStringExtra("url") ?: "not found")
                }

                is PokemonDetailError -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, ErrorFragment.newInstance())
                        .commit()
                }

                is PokemonDetailLoading -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, LoadingFragment.newInstance())
                        .commit()
                }

                is PokemonDetailSuccess -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonDetailFragment.newInstance())
                        .commit()
                }
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.info.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            viewModel.response.observe(this){ response ->
                when(response){
                    is PokemonDetailInitial -> {
                        viewModel.getPokemonSpecies(this, intent?.getStringExtra("url") ?: "not found")
                    }

                    is PokemonDetailError -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, ErrorFragment.newInstance())
                            .commit()
                    }

                    is PokemonDetailLoading -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, LoadingFragment.newInstance())
                            .commit()
                    }

                    is PokemonDetailSuccess -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, PokemonDetailFragment.newInstance())
                            .commit()
                    }
                }
            }
        }

        binding.evolution.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            viewModel.responseEvolution.observe(this){ response ->
                when(response){
                    is PokemonEvolutionInitial -> {
                        viewModel.getEvolution(this)
                    }

                    is PokemonEvolutionError -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, ErrorFragment.newInstance())
                            .commit()
                    }

                    is PokemonEvolutionLoading -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, LoadingFragment.newInstance())
                            .commit()
                    }

                    is PokemonEvolutionSuccess -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, PokemonEvolutionFragment.newInstance())
                            .commit()
                    }
                }
            }

        }

        binding.location.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            viewModel.responseLocation.observe(this){ response ->
                when(response){
                    is PokemonLocationInitial -> {
                        viewModel.getLocation(this)
                    }

                    is PokemonLocationError -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, ErrorFragment.newInstance())
                            .commit()
                    }

                    is PokemonLocationLoading -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, LoadingFragment.newInstance())
                            .commit()
                    }

                    is PokemonLocationSuccess -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, PokemonLocationFragment.newInstance())
                            .commit()
                    }
                }
            }
        }

        binding.moves.setOnClickListener {
                deselectAll()
                it.alpha = 1.0F

            viewModel.response.observe(this){ response ->
                when(response){
                    is PokemonDetailInitial -> {
                        viewModel.getPokemonSpecies(this, intent?.getStringExtra("url") ?: "not found")
                    }

                    is PokemonDetailError -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, ErrorFragment.newInstance())
                            .commit()
                    }

                    is PokemonDetailLoading -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, LoadingFragment.newInstance())
                            .commit()
                    }

                    is PokemonDetailSuccess -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentView, PokemonMovesFragment.newInstance())
                            .commit()
                    }
                }
            }
        }

    }
    private fun deselectAll(){
        binding.moves.alpha = 0.7F
        binding.info.alpha = 0.7F
        binding.evolution.alpha = 0.7F
        binding.location.alpha = 0.7F
    }
}