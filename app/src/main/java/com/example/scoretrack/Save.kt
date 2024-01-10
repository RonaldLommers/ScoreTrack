package com.example.scoretrack

import java.sql.Array
import java.sql.Time
import java.sql.Date

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
