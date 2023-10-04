package com.itsmobile.pokedex.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itsmobile.pokedex.model.moves.moveDetail.Move

class MoveDetailViewModel: ViewModel() {
    var move: Move? by mutableStateOf(null)
        private set


    fun setMove(context: Context, url:String){

        val queue = Volley.newRequestQueue(context)
        val jsonReq = JsonObjectRequest(
            Request.Method.GET, url, null,
            {response ->
                val move = Gson().fromJson(response.toString(), Move::class.java)

                this.move = move
            },
            {}
        )
        queue.add(jsonReq)
    }
}