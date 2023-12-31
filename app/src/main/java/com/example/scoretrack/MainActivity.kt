package com.example.scoretrack

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playImageButton = findViewById<ImageButton>(R.id.playImageButton)
        playImageButton.setOnClickListener {
            val intent = Intent(this, Play::class.java)
            startActivity(intent)
        }

        val savesImageButton = findViewById<ImageButton>(R.id.savesImageButton)
        savesImageButton.setOnClickListener {
            val intent = Intent(this, Saves::class.java)
            startActivity(intent)
        }

        val settingsImageButton = findViewById<ImageButton>(R.id.settingsImageButton)
        settingsImageButton.setOnClickListener {
            val intent = Intent(this, Accountinstellingen::class.java)
            startActivity(intent)
        }
    }
}