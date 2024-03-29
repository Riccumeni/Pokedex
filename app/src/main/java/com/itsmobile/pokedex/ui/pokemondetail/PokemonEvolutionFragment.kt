package com.itsmobile.pokedex.ui.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsmobile.pokedex.ui.adapters.EvolutionAdapter
import com.itsmobile.pokedex.databinding.FragmentPokemonEvolutionBinding
import com.itsmobile.pokedex.domain.usecases.PokemonEvolutionSuccess
import com.itsmobile.pokedex.domain.viewmodels.PokemonDetailViewModel

class PokemonEvolutionFragment : Fragment() {
    private val evolutionModel : PokemonDetailViewModel by activityViewModels()

    private var _binding: FragmentPokemonEvolutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonEvolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        evolutionModel.responseEvolution.observe(viewLifecycleOwner){ evolutions ->
            binding.evolutionRecycler.apply {
                adapter = EvolutionAdapter((evolutions as PokemonEvolutionSuccess).evolutions)
                layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonEvolutionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}