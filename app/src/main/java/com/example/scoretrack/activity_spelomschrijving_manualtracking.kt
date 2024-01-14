package com.example.scoretrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity_spelomschrijving_manualtracking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spelomschrijving_manualtracking)

        val button = findViewById<Button>(R.id.bj_terug)

        button.setOnClickListener {
            val intent = Intent(this, activity_spelomschrijving_manualtracking::class.java)
            startActivity(intent)
        }
    }
}


