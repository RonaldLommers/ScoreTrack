package com.example.scoretrack

import android.content.Intent
class activity_spelomschrijving_blackjack : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelomschrijving_blackjack)
    }
}




//button spelomschrijving -> spelkeuze

val button = findViewById<ImageButton>(R.id.bj_terug)

button.setOnClickListener {
    val doorsturen = Intent(this, activity_spelomschrijving_blackjack::class.java)
    startActivity(doorsturen)
}

