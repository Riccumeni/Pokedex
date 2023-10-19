package com.itsmobile.pokedex.ui.pokedexlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsmobile.pokedex.ui.adapters.PokemonAdapter
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.common.VersionHelper
import com.itsmobile.pokedex.ui.version.VersionActivity
import com.itsmobile.pokedex.databinding.ActivityPokedexBinding
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

        binding.ver.text = viewModel.version.value?.let { VersionHelper.getRomanVersionByUrl(it) }

        viewModel.version.value?.let {
            binding.ver.text = VersionHelper.getRomanVersionByUrl(it)
        }

        viewModel.getPokemonListFilteredByGen(viewModel.version.value.toString(), this)



        binding.searchEditText.addTextChangedListener {
            viewModel.getPokemonListFilteredOnText(it.toString())
        }

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

        viewModel.response.observe(this){ response ->
            if(response.status == 200){
                viewModel.pokedexListFiltered.observe(this){
                    if(it !== null){
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                        val pokemonAdapter = PokemonAdapter(it)

                        binding.searchEditText.visibility = View.VISIBLE
                        recyclerView.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        binding.errorSection.visibility = View.GONE

                        recyclerView.apply {
                            adapter = pokemonAdapter
                            layoutManager = LinearLayoutManager(this@PokedexActivity, LinearLayoutManager.VERTICAL, false)
                        }
                    }

                }
            }else{
                binding.progressCircular.visibility = View.GONE
                binding.searchEditText.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.errorSection.visibility = View.VISIBLE
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.getPokemonListFilteredByGen(viewModel.version.value.toString(), this)
        }

    }

    override fun onResume() {
        super.onResume()

        viewModel.getVersion(this)

        binding.ver.text = viewModel.version.value?.let { VersionHelper.getRomanVersionByUrl(it) }

        viewModel.getPokemonListFilteredByGen(viewModel.version.value.toString(), this)
    }

    private val onBackPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }
    }
}