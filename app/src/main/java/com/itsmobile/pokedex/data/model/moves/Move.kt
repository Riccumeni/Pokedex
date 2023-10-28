package com.itsmobile.pokedex.data.model.moves

data class Move(
    var id: String = "",
    var category: String = "",
    var name: String = "",
    var type: String = "",
    var url: String
){
    constructor() : this("", "", "", "", "")
}
