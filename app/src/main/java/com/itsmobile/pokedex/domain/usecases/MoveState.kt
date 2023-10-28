package com.itsmobile.pokedex.domain.usecases

import com.itsmobile.pokedex.data.model.moves.Move

open class MoveState()

class MoveInitial: MoveState()

class MoveLoading: MoveState()

class MoveError: MoveState()

class MoveListSuccess(val move: ArrayList<Move>) : MoveState()

class MoveDetailSuccess(val move: com.itsmobile.pokedex.data.model.moves.moveDetail.Move) : MoveState()