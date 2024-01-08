package com.example.scoretrack

import java.sql.Array
import java.sql.Time
import java.sql.Date

data class SubSave(
    val datumHervat: Date,
    val datumOpgeslagen: Date,
    val gamestate: Array
)

data class Save(
    val id: Int,
    val opmerking: String,
    val speltype: String,
    val startdatum: Date,
    val spelers: Array,
    val saves: Array
)
