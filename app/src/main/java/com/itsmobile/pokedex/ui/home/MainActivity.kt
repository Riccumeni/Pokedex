package com.itsmobile.pokedex.ui.home

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import com.itsmobile.pokedex.ui.pokedexlist.PokedexActivity
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.ui.team.TeamActivity
import com.itsmobile.pokedex.ui.version.VersionActivity
import com.itsmobile.pokedex.databinding.ActivityMainBinding
import com.itsmobile.pokedex.ui.moveslist.MoveActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)

        val sharedPrefs = getSharedPreferences("version", Context.MODE_PRIVATE)
        binding.versionTextview.text = sharedPrefs.getString("version_num", "I")

        binding.versionContainer.setOnClickListener {
            startActivity(Intent(this, VersionActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }

        binding.pokedexCard.setOnClickListener {
            startActivity(Intent(this, PokedexActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }

        binding.teamCard.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }

        binding.teamPokeballImageview.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }

        binding.movesPage.setOnClickListener {
            startActivity(Intent(this, MoveActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_anim)
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPrefs = getSharedPreferences("version", Context.MODE_PRIVATE)
        binding.versionTextview.text = sharedPrefs.getString("version_num", "I")
    }
}