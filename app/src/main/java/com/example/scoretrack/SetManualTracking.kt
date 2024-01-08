package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SetManualTrackingActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var integerEditText: EditText
    private lateinit var name1EditText: EditText
    // Add references for other name EditText widgets as needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_manual_tracking)

        // Retrieve references to EditText widgets
        titleEditText = findViewById(R.id.titleEditText)
        integerEditText = findViewById(R.id.integerEditText)
        name1EditText = findViewById(R.id.name1EditText)
        // Add references for other name EditText widgets as needed

        val startButton: Button = findViewById(R.id.start)
        startButton.setOnClickListener {
            // Create an Intent to start ManualTrackingActivity
            val intent = Intent(this, ManualTrackingActivity::class.java)

            // Pass data to ManualTrackingActivity using Intent extras
            intent.putExtra("title", titleEditText.text.toString())
            intent.putExtra("integerValue", integerEditText.text.toString().toIntOrNull() ?: 0)
            intent.putExtra("name1", name1EditText.text.toString())
            // Add extras for other names as needed

            // Start ManualTrackingActivity with the provided data
            startActivity(intent)
        }
    }
}
