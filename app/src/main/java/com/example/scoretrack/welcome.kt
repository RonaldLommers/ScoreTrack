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


/* spel 2 ( Poker )*/


val button = findViewById<ImageButton>(R.id.poker)

button.setOnClickListener {
    val intent = Intent(this, activity_poker::class.java)
    startActivity(intent)
}




/* spel 3 ( ManualTracking )*/

val button = findViewById<ImageButton>(R.id.manualTracking)

button.setOnClickListener {
    val intent = Intent(this, activity_manualTracking::class.java)
    startActivity(intent)
}







/* spel 4 ( comingSoon )*/

val button = findViewById<ImageButton>(R.id.comingSoon)

button.setOnClickListener {
    val intent = Intent(this, activity_comingSoon::class.java)
    startActivity(intent)
}