//

package com.example.scoretrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.TableRow
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

        val dn1: TextView = findViewById(R.id.DN1)
        val dn2: TextView = findViewById(R.id.DN2)
        val dn3: TextView = findViewById(R.id.DN3)
        val dn4: TextView = findViewById(R.id.DN4)
        val dn5: TextView = findViewById(R.id.DN5)

        val int1: TextView = findViewById(R.id.displayInteger1)
        val int2: TextView = findViewById(R.id.displayInteger2)
        val int3: TextView = findViewById(R.id.displayInteger3)
        val int4: TextView = findViewById(R.id.displayInteger4)
        val int5: TextView = findViewById(R.id.displayInteger5)

        val i1 = findViewById<EditText>(R.id.I1)
        val i2 = findViewById<EditText>(R.id.I2)
        val i3 = findViewById<EditText>(R.id.I3)
        val i4 = findViewById<EditText>(R.id.I4)
        val i5 = findViewById<EditText>(R.id.I5)

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
        displayName1.text = "$name1: "
        displayName2.text = "$name2: "
        displayName3.text = "$name3: "
        displayName4.text = "$name4: "
        displayName5.text = "$name5: "
        dn1.text = "$name1: "
        dn2.text = "$name2: "
        dn3.text = "$name3: "
        dn4.text = "$name4: "
        dn5.text = "$name5: "
        int1.text = integerValue.toString()
        int2.text = integerValue.toString()
        int3.text = integerValue.toString()
        int4.text = integerValue.toString()
        int5.text = integerValue.toString()


        var value1 = integerValue.toString().toIntOrNull() ?:0
        var value2 = integerValue.toString().toIntOrNull() ?:0
        var value3 = integerValue.toString().toIntOrNull() ?:0
        var value4 = integerValue.toString().toIntOrNull() ?:0
        var value5 = integerValue.toString().toIntOrNull() ?:0
        val addButton = findViewById<Button>(R.id.addScore)
        addButton.setOnClickListener{
            //add integers of player-scores to the textview
            val et1 = i1.text.toString().toIntOrNull() ?:0
            val et2 = i2.text.toString().toIntOrNull() ?:0
            val et3 = i3.text.toString().toIntOrNull() ?:0
            val et4 = i4.text.toString().toIntOrNull() ?:0
            val et5 = i5.text.toString().toIntOrNull() ?:0

            value1 += et1
            value2 += et2
            value3 += et3
            value4 += et4
            value5 += et5

            int1.text = int1.text.toString()+ " " + value1
            int2.text = int2.text.toString()+ " " + value2
            int3.text = int3.text.toString()+ " " + value3
            int4.text = int4.text.toString()+ " " + value4
            int5.text = int5.text.toString()+ " " + value5

        }

        val tr1 = findViewById<TableRow>(R.id.TR1)
        val tr2 = findViewById<TableRow>(R.id.TR2)
        val tr3 = findViewById<TableRow>(R.id.TR3)
        val tr4 = findViewById<TableRow>(R.id.TR4)
        val tr5 = findViewById<TableRow>(R.id.TR5)
        val etr1 = findViewById<TableRow>(R.id.ETR1)
        val etr2 = findViewById<TableRow>(R.id.ETR2)
        val etr3 = findViewById<TableRow>(R.id.ETR3)
        val etr4 = findViewById<TableRow>(R.id.ETR4)
        val etr5 = findViewById<TableRow>(R.id.ETR5)

        //hide unfilled player names
        if (name1 == ""){
            tr1.visibility = View.GONE
            etr1.visibility = View.GONE
        }
        if (name2 == ""){
            tr2.visibility = View.GONE
            etr2.visibility = View.GONE
        }
        if (name3 == ""){
            tr3.visibility = View.GONE
            etr3.visibility = View.GONE
        }
        if (name4 == ""){
            tr4.visibility = View.GONE
            etr4.visibility = View.GONE
        }
        if (name5 == ""){
            tr5.visibility = View.GONE
            etr5.visibility = View.GONE
        }



    }
}

