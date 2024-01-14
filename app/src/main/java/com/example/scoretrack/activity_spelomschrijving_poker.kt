package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_spelomschrijving_poker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelomschrijving_poker)

        val button = findViewById<Button>(R.id.bj_terug)
        button.setOnClickListener {
            val intent = Intent(this, spelkeuze::class.java)
            startActivity(intent)
        }

        val closeButton = findViewById<Button>(R.id.sluiten)
        closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

