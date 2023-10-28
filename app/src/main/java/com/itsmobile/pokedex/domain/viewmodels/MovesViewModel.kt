package com.itsmobile.pokedex.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.mutableStateOf
import com.itsmobile.pokedex.data.model.moves.Move
import com.itsmobile.pokedex.domain.usecases.MoveError
import com.itsmobile.pokedex.domain.usecases.MoveInitial
import com.itsmobile.pokedex.domain.usecases.MoveListSuccess
import com.itsmobile.pokedex.domain.usecases.MoveLoading
import com.itsmobile.pokedex.domain.usecases.MoveState

class MovesViewModel: ViewModel() {
    val state = mutableStateOf(MoveState())

    init {
        state.value = MoveInitial()
        viewModelScope.launch {
            setMoves()
        }
    }

    private suspend fun setMoves(){
        val db = FirebaseFirestore.getInstance()

        val movesTemp = ArrayList<Move>()

        try {
            state.value = MoveLoading()

            db.collection("/moves").get().await().map {
                val result = it.toObject(Move::class.java)

                movesTemp.add(result)
            }
            state.value = MoveListSuccess(movesTemp)
        }catch (e: FirebaseFirestoreException){
            state.value = MoveError()
        }
    }
}