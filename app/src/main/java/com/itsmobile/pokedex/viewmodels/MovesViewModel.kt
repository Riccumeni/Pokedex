package com.itsmobile.pokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.itsmobile.pokedex.model.moves.Move
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.mutableStateOf

class MovesViewModel: ViewModel() {
    val state = mutableStateOf(ArrayList<Move>())

    init {
        viewModelScope.launch {
            setMoves()
        }
    }

    private suspend fun setMoves(){
        val db = FirebaseFirestore.getInstance()

        val movesTemp = ArrayList<Move>()

        try {
            db.collection("/moves").get().await().map {
                val result = it.toObject(Move::class.java)

                movesTemp.add(result)
            }
        }catch (e: FirebaseFirestoreException){

        }

        if(movesTemp.isNotEmpty()){
            state.value = movesTemp
        }

    }
}