package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class spelkeuze : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelkeuze)

        val blackjackButton = findViewById<ImageButton>(R.id.blackjack)
        blackjackButton.setOnClickListener {
            val intent = Intent(this@spelkeuze, Activity_spelomschrijving_blackjack::class.java)
            startActivity(intent)
        }

        val pokerButton = findViewById<ImageButton>(R.id.poker)
        pokerButton.setOnClickListener {
            val intent = Intent(this@spelkeuze, activity_spelomschrijving_poker::class.java)
            startActivity(intent)
        }

        val comingsoonButton = findViewById<ImageButton>(R.id.yahtzee)
        comingsoonButton.setOnClickListener {
            val intent = Intent(this@spelkeuze, Activity_spelomschrijving_yahtzee::class.java)
            startActivity(intent)
        }

        val manualTrackingButton = findViewById<ImageButton>(R.id.manualTracking)
        manualTrackingButton.setOnClickListener {
            val intent = Intent(this@spelkeuze,activity_spelomschrijving_manualtracking::class.java)
            startActivity(intent)
        }
    }
}
