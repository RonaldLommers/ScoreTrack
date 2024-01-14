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
            val intent = Intent(this@spelkeuze, Blackjack::class.java)
            startActivity(intent)
        }


        val manualTrackingButton = findViewById<ImageButton>(R.id.manualTracking)
        manualTrackingButton.setOnClickListener {
            val intent = Intent(this@spelkeuze,SetManualTrackingActivity::class.java)
            startActivity(intent)
        }
    }
}
