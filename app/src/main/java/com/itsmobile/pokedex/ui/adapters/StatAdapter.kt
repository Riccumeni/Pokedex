package com.itsmobile.pokedex.ui.adapters

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.itsmobile.pokedex.R


class StatAdapter(private val stats: ArrayList<com.itsmobile.pokedex.data.model.Stat>) : RecyclerView.Adapter<StatAdapter.CustomViewHolder>() {
    class CustomViewHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_stat, parent, false) as ViewGroup
        return CustomViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ObjectAnimatorBinding")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val stat = stats[position]


        when(stat.stat.name){
            "hp" -> holder.view.findViewById<TextView>(R.id.name).text = "HP"
            "attack" -> holder.view.findViewById<TextView>(R.id.name).text = "ATK"
            "defense" -> holder.view.findViewById<TextView>(R.id.name).text = "DEF"
            "special-attack" -> holder.view.findViewById<TextView>(R.id.name).text = "SATK"
            "special-defense" -> holder.view.findViewById<TextView>(R.id.name).text = "SDEF"
            "speed" -> holder.view.findViewById<TextView>(R.id.name).text = "SPD"
            "tot" -> holder.view.findViewById<TextView>(R.id.name).text = "TOT"
        }

        holder.view.findViewById<TextView>(R.id.value).text = stat.base_stat.toInt().toString()

        var maxStat = 150

        if(position == stats.size - 1){
            maxStat = 700
        }

        val progressBar: ProgressBar = holder.view.findViewById(R.id.barInside)

        progressBar.max = maxStat

        ObjectAnimator.ofInt(holder.view.findViewById<ProgressBar>(R.id.barInside), "progress", stat.base_stat.toInt())
            .setDuration(2000)
            .start()
    }

    override fun getItemCount() = stats.size
}