package com.example.scoretrack

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

var gamestate = object {}

fun newGame(gameType: String){
    gamestate = object {}
}
fun storeValue(){

}
fun getValue(){

}