package com.itsmobile.pokedex.data.model.pokemon

import com.itsmobile.pokedex.data.model.Ability
import com.itsmobile.pokedex.data.model.Stat
import com.itsmobile.pokedex.data.model.Type

class Pokemon(var moves: ArrayList<MoveVersion>, var base_experience: Int, var name: String, var stats: ArrayList<com.itsmobile.pokedex.data.model.Stat>, val sprites: Sprite, val abilities: ArrayList<com.itsmobile.pokedex.data.model.Ability>, val weight: Int, val height: Int, val types: ArrayList<TypeOutside>) {

    var movesFilteredByLevel = ArrayList<MoveVersion>()
    var movesFilteredByMachine = ArrayList<MoveVersion>()
    var movesFilteredByEggs = ArrayList<MoveVersion>()

    fun filterMovesByGeneration(gameVersion: String) : ArrayList<MoveVersion>{

        var movesFiltered = ArrayList<MoveVersion>()
        moves.let {
            moves.forEachIndexed{ i, move ->
                move.version_group_details.let {
                    move.version_group_details.forEachIndexed { index, version ->
                        if(version.version_group.name == gameVersion){
                            val versionGroup = ArrayList<VersionGroupDetail>()
                            versionGroup.add(version)
                            movesFiltered.add(MoveVersion(move.move, versionGroup))
                        }
                    }
                }
            }
        }
        return movesFiltered
    }

    fun getFilteredMoves(method: String, version: String) : ArrayList<MoveVersion>{
        var movesFiltered = filterMovesByGeneration(version)

        var movesLevel = ArrayList<MoveVersion>()

        movesFiltered.let {
            movesFiltered.forEachIndexed{ i, move ->
                if(move.version_group_details[0].move_learn_method.name == method){
                    movesLevel.add(move)
                }
            }

            return movesLevel
        }
    }
}