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
            val intent = Intent(this, activity_spelomschrijving_poker::class.java)
            startActivity(intent)
        }
    }
}

