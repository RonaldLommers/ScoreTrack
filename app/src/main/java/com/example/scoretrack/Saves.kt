package com.example.scoretrack

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.File
import java.io.IOException
import java.io.InputStream


class Saves : AppCompatActivity() {

    var savesPath = "saves.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saves)
        loadSaves()
    }

    fun loadSaves() {
        val textView = findViewById<TextView>(R.id.tv_saves)
        val jsonFile = loadJSONFromAsset(savesPath)

//        val gson = Gson()
//        val save:Save = gson.fromJson(jsonFile, Save::class.java)
        textView.text = jsonFile
//        println("save ID : ${save.id}")
//        println("save SpelType : ${save.speltype}")
    }

    fun loadJSONFromAsset(jsonPath: String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = getAssets().open(jsonPath)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}

