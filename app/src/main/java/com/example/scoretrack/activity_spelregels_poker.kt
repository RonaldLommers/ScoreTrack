package com.example.scoretrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity_spelregels_poker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelregels_poker)

        val button = findViewById<Button>(R.id.posr_terug)

        button.setOnClickListener {
            val doorsturen = Intent(this, activity_spelregels_poker::class.java)
            startActivity(doorsturen)
        }
    }
}