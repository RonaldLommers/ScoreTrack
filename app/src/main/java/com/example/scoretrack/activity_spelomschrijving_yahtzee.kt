package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_spelomschrijving_yahtzee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelomschrijving_yahtzee)

        val button = findViewById<Button>(R.id.y_terug)
        button.setOnClickListener {
            val doorsturen = Intent(this, spelkeuze::class.java)
            startActivity(doorsturen)
        }

        val startButton = findViewById<Button>(R.id.y_spelen)
        startButton.setOnClickListener {
            val doorsturen = Intent(this, Blackjack::class.java)
            startActivity(doorsturen)
        }

        val closeButton = findViewById<Button>(R.id.sluiten)
        closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val spelregels = findViewById<Button>(R.id.spelregelsy)
        spelregels.setOnClickListener {
            val intent = Intent(this, activity_spelregels_blackjack::class.java)
            startActivity(intent)
        }
    }
}
