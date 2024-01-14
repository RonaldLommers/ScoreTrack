package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_spelomschrijving_blackjack : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelomschrijving_blackjack)

        val button = findViewById<Button>(R.id.bj_terug)
        button.setOnClickListener {
            val doorsturen = Intent(this, activity_spelomschrijving_blackjack::class.java)
            startActivity(doorsturen)
        }
    }
}
