package com.itsmobile.pokedex.ui.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsmobile.pokedex.ui.adapters.MoveAdapter
import com.itsmobile.pokedex.databinding.FragmentPokemonMovesBinding
import com.itsmobile.pokedex.domain.usecases.PokemonDetailSuccess
import com.itsmobile.pokedex.domain.viewmodels.PokemonDetailViewModel

class PokemonMovesFragment : Fragment() {
    private var _binding: FragmentPokemonMovesBinding? = null
    private val binding get() = _binding!!

    private val pokemonModel : PokemonDetailViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonMovesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonModel.response.observe(viewLifecycleOwner){ response ->

            val pokemon = (response as PokemonDetailSuccess).pokemon

            binding.byLevelRecycler.apply {
                adapter = MoveAdapter(pokemon.movesFilteredByLevel, "level-up")
                layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
            }

            binding.byMachinesRecycler.apply {
                adapter = MoveAdapter(pokemon.movesFilteredByMachine, "machine")
                layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
            }

            if(pokemon.movesFilteredByEggs.size > 0){
                binding.byEggsRecycler.apply {
                    adapter = MoveAdapter(pokemon.movesFilteredByEggs, "egg")
                    layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
                }
            }else{
                binding.byEggsRecycler.isGone = true
                binding.byEggsTitle.isGone = true
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonMovesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}