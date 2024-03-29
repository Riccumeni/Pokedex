package com.itsmobile.pokedex.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.data.model.Ability
import com.itsmobile.pokedex.data.model.ability.EffectEntries
import java.util.*
import kotlin.collections.ArrayList

class AbilityAdapter (private var abilities: ArrayList<com.itsmobile.pokedex.data.model.Ability>): RecyclerView.Adapter<TypeAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_ability, parent, false) as ViewGroup
        return TypeAdapter.CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: TypeAdapter.CustomViewHolder, position: Int) {
        val ability = abilities[position]

        holder.view.findViewById<TextView>(R.id.title).text = ability.ability.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        holder.view.findViewById<TextView>(R.id.description).text = ability.ability.description ?: "error getting data"
    }

    override fun getItemCount(): Int = abilities.size
}