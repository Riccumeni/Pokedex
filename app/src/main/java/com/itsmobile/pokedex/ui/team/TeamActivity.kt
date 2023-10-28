package com.itsmobile.pokedex.ui.team

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.databinding.ActivityTeamBinding
import com.itsmobile.pokedex.data.model.pokemon.PokemonEntry
import com.itsmobile.pokedex.data.model.pokemon.PokemonItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)

        val shakeLeftAnim = AnimationUtils.loadAnimation(this@TeamActivity, R.anim.shake_left)
        val shakeRightAnim = AnimationUtils.loadAnimation(this@TeamActivity, R.anim.shake_right)
        val shakeToCenterLeftAnim = AnimationUtils.loadAnimation(this@TeamActivity,
            R.anim.shake_to_center_left
        )
        val shakeToCenterRightAnim = AnimationUtils.loadAnimation(this@TeamActivity,
            R.anim.shake_to_center_right
        )

        val pokeballList = listOf(binding.pokeball1, binding.pokeball2, binding.pokeball3, binding.pokeball4, binding.pokeball5, binding.pokeball6)
        val viewList = listOf(binding.pokemon1, binding.pokemon2, binding.pokemon3, binding.pokemon4, binding.pokemon5, binding.pokemon6)

        val sharedPref = this.getSharedPreferences("version", Context.MODE_PRIVATE)
        val version = sharedPref.getString("version", "")

        val r = findViewById<ImageButton>(R.id.randomButton)
        r.setOnClickListener {
            var animCounter = 0

            lifecycleScope.launch {

                while (animCounter != viewList.size) {
                    pokeballList[animCounter].startAnimation(shakeRightAnim)
                    animCounter++
                }
                delay(shakeRightAnim.duration)
                animCounter = 0

                while (animCounter != viewList.size) {
                    pokeballList[animCounter].startAnimation(shakeToCenterRightAnim)
                    animCounter++
                }
                delay(shakeToCenterRightAnim.duration)
                animCounter = 0

                while (animCounter != viewList.size) {
                    pokeballList[animCounter].startAnimation(shakeLeftAnim)
                    animCounter++
                }
                delay(shakeRightAnim.duration)
                animCounter = 0

                while (animCounter < viewList.size) {
                    pokeballList[animCounter].startAnimation(shakeToCenterLeftAnim)
                    animCounter++
                }
                delay(shakeToCenterRightAnim.duration)

                sendAPIRequest(version.toString(), viewList)
            }
        }

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }
    }


    private fun getRandomPokemon(): String {
        val rand = Random.nextInt(1, 1000)
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$rand.png"
    }

    private val onBackPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }
    }

    private fun sendAPIRequest(gen:String, viewList: List<ImageView>) {
        val queque = Volley.newRequestQueue(this)
        val jsonReq = JsonObjectRequest(
            Request.Method.GET, gen, null,
            {response ->
                val poke = Gson().fromJson(response.toString(), PokemonItem::class.java)

                var numbers = ArrayList<String>()

                val pokemonEntries = ArrayList<PokemonEntry>()
                for (pokemonEntry in poke.pokemon_entries) {
                    numbers.add(getFinalNumberFromUrl(pokemonEntry.pokemon_species.url))
                    //pokemonEntries.add(PokemonEntry(pokemonEntry.entry_number, PokemonSpecies(pokemonEntry.pokemon_species.name,pokemonEntry.pokemon_species.url)))
                }



                var i = 0;
                while (i<6){
                    val rand = Random.nextInt(0, numbers.size)
                    Glide.with(this@TeamActivity).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${numbers[rand]}.png").centerInside().into(viewList[i])
                    viewList[i].visibility = View.VISIBLE
                    i++
                }

            },
            {}
        )
        queque.add(jsonReq)
    }

    private fun getFinalNumberFromUrl(url:String) : String{
        val regex = Regex("""\d+(?=/?$)""")
        val matchResult = regex.find(url)
        return matchResult?.value.toString()
    }
}