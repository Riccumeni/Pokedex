package com.itsmobile.pokedex.domain.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.data.model.moves.moveDetail.Move
import com.itsmobile.pokedex.domain.usecases.MoveDetailSuccess
import com.itsmobile.pokedex.domain.usecases.MoveError
import com.itsmobile.pokedex.domain.usecases.MoveInitial
import com.itsmobile.pokedex.domain.usecases.MoveLoading
import com.itsmobile.pokedex.domain.usecases.MoveState

class MoveDetailViewModel: ViewModel() {
    var state: MoveState by mutableStateOf(MoveLoading())
        private set


    fun setMove(context: Context, url:String){

        val queue = Volley.newRequestQueue(context)
        val jsonReq = JsonObjectRequest(
            Request.Method.GET, url, null,
            {response ->
                val move = Gson().fromJson(response.toString(), Move::class.java)

                this.state = MoveDetailSuccess(move)
            },
            {
                this.state = MoveError()
            }
        )
        queue.add(jsonReq)
    }
}