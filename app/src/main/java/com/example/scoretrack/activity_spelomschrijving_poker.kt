package com.example.scoretrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class activity_spelomschrijving_poker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelomschrijving_poker)
    }
}


//button spelomschrijving -> spelkeuze

val button = findViewById<ImageButton>(R.id.bj_terug)

button.setOnClickListener {
    val intent = Intent(this, activity_spelomschrijving_poker::class.java)
    startActivity(intent)
}
