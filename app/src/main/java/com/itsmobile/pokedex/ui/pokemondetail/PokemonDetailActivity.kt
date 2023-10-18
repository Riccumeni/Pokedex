package com.itsmobile.pokedex.ui.pokemondetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.databinding.ActivityPokemonDetailBinding
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