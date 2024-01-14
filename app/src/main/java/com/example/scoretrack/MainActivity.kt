package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playImageButton = findViewById<ImageButton>(R.id.playImageButton)
        playImageButton.setOnClickListener {
            val intent = Intent(this, spelkeuze::class.java)
            startActivity(intent)
        }

        val savesImageButton = findViewById<ImageButton>(R.id.savesImageButton)
        savesImageButton.setOnClickListener {
            val intent = Intent(this, Saves::class.java)
            startActivity(intent)
        }
        }
    }