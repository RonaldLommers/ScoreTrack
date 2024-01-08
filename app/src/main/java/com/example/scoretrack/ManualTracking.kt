package com.example.scoretrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManualTrackingActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_tracking)

        // Retrieve references to TextView widgets
        val displayTitle: TextView = findViewById(R.id.displayTitle)
        val displayInteger: TextView = findViewById(R.style.displayInteger)
        val displayName1: TextView = findViewById(R.id.displayName1)
        val displayName2: TextView = findViewById(R.id.displayName2)
        val displayName3: TextView = findViewById(R.id.displayName3)
        val displayName4: TextView = findViewById(R.id.displayName4)
        val displayName5: TextView = findViewById(R.id.displayName5)
        // Add references for other name TextView widgets as needed

        // Retrieve data from Intent extras
        val title = intent.getStringExtra("title") ?: ""
        val integerValue = intent.getIntExtra("integerValue", 0)
        val name1 = intent.getStringExtra("name1") ?: ""
        val name2 = intent.getStringExtra("name2") ?: ""
        val name3 = intent.getStringExtra("name3") ?: ""
        val name4 = intent.getStringExtra("name4") ?: ""
        val name5 = intent.getStringExtra("name5") ?: ""
        // Retrieve data for other names as needed

        // Update TextViews with the retrieved data
        displayTitle.text = "Title: $title"
//        displayInteger1.text = "Integer Value: $integerValue"
        displayName1.text = "Name 1: $name1"
        displayName2.text = "Name 2: $name2"
        displayName3.text = "Name 3: $name3"
        displayName4.text = "Name 4: $name4"
        displayName5.text = "Name 5: $name5"
        // Update other name TextViews as needed
    }
}


