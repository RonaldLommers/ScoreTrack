//

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
        val displayName1: TextView = findViewById(R.id.displayName1)
        val displayName2: TextView = findViewById(R.id.displayName2)
        val displayName3: TextView = findViewById(R.id.displayName3)
        val displayName4: TextView = findViewById(R.id.displayName4)
        val displayName5: TextView = findViewById(R.id.displayName5)
        val int1: TextView = findViewById(R.id.displayInteger1)
        val int2: TextView = findViewById(R.id.displayInteger2)
        val int3: TextView = findViewById(R.id.displayInteger3)
        val int4: TextView = findViewById(R.id.displayInteger4)
        val int5: TextView = findViewById(R.id.displayInteger5)
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
        displayTitle.text = title
        displayName1.text = name1
        displayName2.text = name2
        displayName3.text = name3
        displayName4.text = name4
        displayName5.text = name5
        int1.text = integerValue.toString()
        int2.text = integerValue.toString()
        int3.text = integerValue.toString()
        int4.text = integerValue.toString()
        int5.text = integerValue.toString()
        // Update other name TextViews as needed
    }
}


