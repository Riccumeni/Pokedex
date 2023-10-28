package com.itsmobile.pokedex.ui.version

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.common.VersionHelper
import com.itsmobile.pokedex.databinding.ActivityVersionBinding
import com.itsmobile.pokedex.data.model.Version
import com.itsmobile.pokedex.ui.adapters.VersionAdapter

class VersionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVersionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVersionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.primary)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)

        val sharedPref = getSharedPreferences("version", Context.MODE_PRIVATE)

        val textView = findViewById<TextView>(R.id.numVersion)
        textView.text = sharedPref.getString("version_num", "I")

        loadRecyclerView()

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }
    }

    private fun loadRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this@VersionActivity, LinearLayoutManager.VERTICAL, false)

        val adapter = VersionAdapter(VersionHelper.versions)
        recyclerView.adapter = adapter
    }

    private val onBackPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_out_right)
        }
    }
}