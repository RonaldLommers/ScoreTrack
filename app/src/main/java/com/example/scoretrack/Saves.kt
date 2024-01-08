package com.example.scoretrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import java.io.File

class Saves : AppCompatActivity() {

    var savesPath = "src/main/resources/raw/saves.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saves)
        loadSaves()
    }

    fun loadSaves() {
        val textView = findViewById<TextView>(R.id.tv_saves)
        val jsonFile = File(savesPath)
        val jsonString = jsonFile.readText()
        textView.text = jsonFile.toString()
//        println(jsonString)
//        val gson = Gson()
//        val save:Save = gson.fromJson(jsonString, Save::class.java)
//        println("save ID : ${save.id}")
//        println("save SpelType : ${save.speltype}")
    }
}

