package com.example.scoretrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

    }
}


/* spel 1 ( BlackJack )*/


val button = findViewById<ImageButton>(R.id.blackjack)

button.setOnClickListener {
    val intent = Intent(this, BlackjackActivity::class.java)
    startActivity(intent)
}