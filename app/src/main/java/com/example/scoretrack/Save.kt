package com.example.scoretrack

import java.sql.Array
import java.sql.Time
import java.sql.Date
import java.util.Objects

data class SubSave(
    val datumHervat: String,
    val datumOpgeslagen: String,
    val gamestate: Any,
)

data class Save(
    val id: Int,
    val opmerking: String,
    var speltype: String,
    val startdatum: String,
    val spelers: Collection<String>,
    val saves: Collection<SubSave>
)

var gamestates = object {}

fun newGamestate(gameType: String){
//    gamestates[gameType] = {}
}
fun storeValue(){

}
fun getValue(){

}