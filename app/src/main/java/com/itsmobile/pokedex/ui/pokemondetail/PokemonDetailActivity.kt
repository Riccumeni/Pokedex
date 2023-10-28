package com.itsmobile.pokedex.ui.pokemondetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.domain.viewmodels.InternetConnectionViewModel
import com.itsmobile.pokedex.databinding.ActivityPokemonDetailBinding
import com.itsmobile.pokedex.domain.usecases.PokemonEvolutionError
import com.itsmobile.pokedex.domain.usecases.PokemonEvolutionInitial
import com.itsmobile.pokedex.domain.usecases.PokemonEvolutionLoading
import com.itsmobile.pokedex.domain.usecases.PokemonEvolutionSuccess
import com.itsmobile.pokedex.domain.usecases.PokemonLocationError
import com.itsmobile.pokedex.domain.usecases.PokemonLocationInitial
import com.itsmobile.pokedex.domain.usecases.PokemonLocationLoading
import com.itsmobile.pokedex.domain.usecases.PokemonLocationSuccess
import com.itsmobile.pokedex.domain.usecases.PokemonDetailError
import com.itsmobile.pokedex.domain.usecases.PokemonDetailInitial
import com.itsmobile.pokedex.domain.usecases.PokemonDetailLoading
import com.itsmobile.pokedex.domain.usecases.PokemonDetailSuccess
import com.itsmobile.pokedex.ui.ErrorFragment
import com.itsmobile.pokedex.ui.LoadingFragment
import com.itsmobile.pokedex.domain.viewmodels.PokemonDetailViewModel

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    private val viewModel : PokemonDetailViewModel by viewModels()
    private val internetModel: InternetConnectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)

        binding.backButton.setOnClickListener {
            finish()
        }

        internetModel.isNetworkAvailable(this)

        internetModel.isConnected.observe(this){ isConnected ->
            if(isConnected){
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
            }else{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentView, ErrorFragment.newInstance())
                    .commit()
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            internetModel.isNetworkAvailable(this)
           when(menuItem.itemId){
               R.id.info -> {
                   internetModel.isConnected.observe(this){ isConnected ->
                       if(isConnected){
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
                       }else{
                           supportFragmentManager
                               .beginTransaction()
                               .replace(R.id.fragmentView, ErrorFragment.newInstance())
                               .commit()
                       }
                   }

                   return@setOnItemSelectedListener true
               }
               R.id.evolution -> {
                   internetModel.isConnected.observe(this) {
                       if (it) {
                           viewModel.responseEvolution.observe(this) { response ->
                               when (response) {
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
                                           .replace(
                                               R.id.fragmentView,
                                               LoadingFragment.newInstance()
                                           )
                                           .commit()
                                   }

                                   is PokemonEvolutionSuccess -> {
                                       supportFragmentManager
                                           .beginTransaction()
                                           .replace(
                                               R.id.fragmentView,
                                               PokemonEvolutionFragment.newInstance()
                                           )
                                           .commit()
                                   }
                               }
                           }
                       } else {
                           supportFragmentManager
                               .beginTransaction()
                               .replace(R.id.fragmentView, ErrorFragment.newInstance())
                               .commit()
                       }
                   }
                   return@setOnItemSelectedListener true;

               }
               R.id.locations -> {
                   internetModel.isConnected.observe(this){
                       if(it){
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
                       }else{
                           supportFragmentManager
                               .beginTransaction()
                               .replace(R.id.fragmentView, ErrorFragment.newInstance())
                               .commit()
                       }
                   }
                   return@setOnItemSelectedListener true;
               }
               R.id.moves -> {
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
                   return@setOnItemSelectedListener true;
               }
               else -> true
           }
        }
    }
}