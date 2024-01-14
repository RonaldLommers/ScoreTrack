package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_spelregels_blackjack : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelregels_blackjack)


        val button_bjterug = findViewById<Button>(R.id.bjsr_terug)

        button_bjterug.setOnClickListener {
            val bjterug = Intent(this, activity_spelomschrijving_blackjack::class.java)
            startActivity(bjterug)
        }
    }
}