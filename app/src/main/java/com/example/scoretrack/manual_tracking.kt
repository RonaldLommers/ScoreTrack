package com.example.scoretrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManualTrackingActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_tracking)

        // Retrieve references to TextView widgets
        val displayTitle: TextView = findViewById(R.id.displayTitle)
        val displayInteger: TextView = findViewById(R.id.displayInteger)
        val displayName1: TextView = findViewById(R.id.displayName1)
        // Add references for other name TextView widgets as needed

        // Retrieve data from Intent extras
        val title = intent.getStringExtra("title") ?: ""
        val integerValue = intent.getIntExtra("integerValue", 0)
        val name1 = intent.getStringExtra("name1") ?: ""
        // Retrieve data for other names as needed

        // Update TextViews with the retrieved data
        displayTitle.text = "Title: $title"
        displayInteger.text = "Integer Value: $integerValue"
        displayName1.text = "Name 1: $name1"
        // Update other name TextViews as needed
    }
}


