package com.itsmobile.pokedex.ui.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.common.TypeCalculator
import com.itsmobile.pokedex.ui.adapters.AbilityAdapter
import com.itsmobile.pokedex.ui.adapters.TypeAdapter
import com.itsmobile.pokedex.ui.adapters.StatAdapter
import com.itsmobile.pokedex.databinding.FragmentPokemonDetailBinding
import com.itsmobile.pokedex.domain.usecases.PokemonDetailSuccess
import com.itsmobile.pokedex.domain.viewmodels.PokemonDetailViewModel
import com.itsmobile.pokedex.data.model.pokemon.StatInside

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val pokemonModel : PokemonDetailViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonModel.response.observe(viewLifecycleOwner){ response ->

            val pokemon = (response as PokemonDetailSuccess).pokemon

            Glide.with(this).load(pokemon.sprites.front_default).into(binding.pokemonImageView)
            val imageAnim = AnimationUtils.loadAnimation(view.context, R.anim.scale_anim)

            binding.pokemonImageView.startAnimation(imageAnim)

            binding.pokemonName.text = pokemon.name.uppercase()
            binding.weight.text = pokemon.weight.toString()
            binding.height.text = pokemon.height.toString()
            binding.baseExperience.text = pokemon.base_experience.toString()

            if(pokemon.stats.size < 7){
                var tot = 0.0

                for(stat in pokemon.stats){
                    tot += stat.base_stat
                }

                pokemon.stats.add(com.itsmobile.pokedex.data.model.Stat(tot, StatInside("tot")))
            }

            val statRecycler = binding.statsRecycler

            val statAdapter = StatAdapter(pokemon.stats)

            statRecycler.apply {
                adapter = statAdapter
                layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
            }


            val abilityRecycler = binding.abilityRecycler
            val abilityAdapter = AbilityAdapter(pokemon.abilities)

            val myLinearLayoutManager = object : LinearLayoutManager(requireView().context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            abilityRecycler.adapter = abilityAdapter
            abilityRecycler.layoutManager = myLinearLayoutManager

            val typeRecycler = requireView().findViewById<RecyclerView>(R.id.typeRecycler)
            val typeAdapter = TypeAdapter(pokemon.types)

            typeRecycler.apply {
                adapter = typeAdapter
                layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
            }

            if(pokemon.types.size == 1){
                val (weaknesses, resistances) = TypeCalculator.convertPokemonTypeToTypeOutside(pokemon.types[0].type.name)
                binding.resistancesRecycler.apply{
                    adapter = TypeAdapter(resistances)
                    layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
                }
                binding.weaknessRecycler.apply{
                    adapter = TypeAdapter(weaknesses)
                    layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
                }
            }else{
                val (weaknesses, resistances) = TypeCalculator.calculateWeaknessesAndResistances(pokemon.types[0].type.name, pokemon.types[1].type.name)
                binding.resistancesRecycler.apply{
                    adapter = TypeAdapter(resistances)
                    layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
                }
                binding.weaknessRecycler.apply{
                    adapter = TypeAdapter(weaknesses)
                    layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
                }
            }


        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonDetailFragment().apply {

            }
    }
}