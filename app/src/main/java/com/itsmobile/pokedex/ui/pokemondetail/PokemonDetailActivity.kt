package com.itsmobile.pokedex.ui.pokemondetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.databinding.ActivityPokemonDetailBinding
import com.itsmobile.pokedex.model.pokemon.Pokemon
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

        viewModel.response.observe(this){
            if(it.status == 200 && it.data is Pokemon){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentView, PokemonDetailFragment.newInstance())
                    .commit()
            }else if(it.status == 404){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentView, ErrorFragment.newInstance())
                    .commit()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.info.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            if(viewModel.response.value?.status == 404){
                viewModel.getPokemonSpecies(this, intent?.getStringExtra("url") ?: "not found")
            }

            viewModel.response.observe(this){ response ->
                if(response.status == 200 && response.data is Pokemon){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonDetailFragment.newInstance())
                        .commit()
                }else if(response.status == 404){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, ErrorFragment.newInstance())
                        .commit()
                }else{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, LoadingFragment.newInstance())
                        .commit()
                }
            }
        }

        binding.evolution.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F

            if(viewModel.responseEvolution.value == null || viewModel.responseEvolution.value?.status == 404){
                viewModel.getEvolution(this)
            }

            viewModel.responseEvolution.observe(this){ response ->
                if(response.status == 200 && response.data != null){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonEvolutionFragment.newInstance())
                        .commit()
                }
                else if(response.status == 404){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, ErrorFragment.newInstance())
                        .commit()
                }
                else{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, LoadingFragment.newInstance())
                        .commit()
                }
            }

        }

        binding.location.setOnClickListener {
            deselectAll()
            it.alpha = 1.0F



            if(viewModel.responseLocation.value == null || viewModel.responseLocation.value?.status == 404){
                Log.d("response", viewModel.responseLocation.value.toString())
                viewModel.getLocation(this)
            }

            viewModel.responseLocation.observe(this){ response ->
                if(response.status == 200 && response.data != null){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonLocationFragment.newInstance())
                        .commit()
                }
                else if(response.status == 404){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, ErrorFragment.newInstance())
                        .commit()
                }
                else{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, LoadingFragment.newInstance())
                        .commit()
                }
            }
            }

        binding.moves.setOnClickListener {
                deselectAll()
                it.alpha = 1.0F

            if(viewModel.response.value?.status == 404){
                viewModel.getPokemonSpecies(this, intent?.getStringExtra("url") ?: "not found")
            }

            viewModel.response.observe(this){ response ->
                if(response.status == 200 && response.data is Pokemon){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, PokemonMovesFragment.newInstance())
                        .commit()
                }else if(response.status == 404){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, ErrorFragment.newInstance())
                        .commit()
                }else{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentView, LoadingFragment.newInstance())
                        .commit()
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