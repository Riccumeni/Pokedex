package com.itsmobile.pokedex.ui.pokedexlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.ui.adapters.PokemonAdapter
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.ui.version.VersionActivity
import com.itsmobile.pokedex.databinding.ActivityPokedexBinding
import com.itsmobile.pokedex.model.pokemon.PokemonEntry
import com.itsmobile.pokedex.model.pokemon.PokemonItem
import com.itsmobile.pokedex.model.pokemon.PokemonSpecies
import com.itsmobile.pokedex.viewmodels.PokedexViewModel

class PokedexActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokedexBinding
    private val viewModel : PokedexViewModel by viewModels()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)

        viewModel.getVersion(this)

        binding.ver.text = viewModel.version.value?.let { getRomanVersionByUrl(it) }

        viewModel.getPokemonListFilteredByGen(viewModel.version.value.toString(), this)

        // BUTTONS

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }

        binding.view2.setOnClickListener {
            startActivity(Intent(this, VersionActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }

        binding.ver.setOnClickListener {
            startActivity(Intent(this, VersionActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }

        // RECYCLER VIEW

        viewModel.pokedexList.observe(this){
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val pokemonAdapter = PokemonAdapter(it)

            recyclerView.apply {
                adapter = pokemonAdapter
                layoutManager = LinearLayoutManager(this@PokedexActivity, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getVersion(this)

        binding.ver.text = viewModel.version.value?.let { getRomanVersionByUrl(it) }

        viewModel.getPokemonListFilteredByGen(viewModel.version.value.toString(), this)
    }

    private val onBackPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }
    }

    private fun getRomanVersionByUrl(url: String): String{
        var numberVersion = ""
        when (url) {
            "https://pokeapi.co/api/v2/pokedex/2"-> numberVersion = "I"
            "https://pokeapi.co/api/v2/pokedex/3" -> numberVersion = "II"
            "https://pokeapi.co/api/v2/pokedex/4" -> numberVersion = "III"
            "https://pokeapi.co/api/v2/pokedex/5" -> numberVersion = "IV"
            "https://pokeapi.co/api/v2/pokedex/8" -> numberVersion = "V"
            "https://pokeapi.co/api/v2/pokedex/12" -> numberVersion = "VI"
            "https://pokeapi.co/api/v2/pokedex/16" -> numberVersion = "VII"
        }
        return numberVersion
    }
}