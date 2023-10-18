package com.itsmobile.pokedex.ui.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsmobile.pokedex.ui.adapters.LocationAdapter
import com.itsmobile.pokedex.databinding.FragmentPokemonLocationBinding
import com.itsmobile.pokedex.model.location.LocationsItem
import com.itsmobile.pokedex.viewmodels.LocationViewModel
import com.itsmobile.pokedex.viewmodels.PokemonDetailViewModel

class PokemonLocationFragment : Fragment() {

    private val locationModel : PokemonDetailViewModel by activityViewModels()

    private var _binding: FragmentPokemonLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationModel.responseLocation.observe(viewLifecycleOwner){ locations ->
            if((locations.data as ArrayList<LocationsItem>).size  == 0){
                binding.isPokemonFindable.visibility = View.VISIBLE
                binding.locations.visibility = View.GONE
            }else{
                binding.locations.apply {
                    adapter = LocationAdapter(locations.data as ArrayList<LocationsItem>)
                    layoutManager = LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonLocationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}