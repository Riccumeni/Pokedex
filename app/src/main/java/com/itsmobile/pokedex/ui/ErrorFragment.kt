package com.itsmobile.pokedex.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.viewmodels.PokemonDetailViewModel

class ErrorFragment : Fragment() {

    private val pokemonModel : PokemonDetailViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retryButton = view.findViewById<Button>(R.id.retryButton)

        retryButton.setOnClickListener {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ErrorFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}