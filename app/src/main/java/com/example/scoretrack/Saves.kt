package com.example.scoretrack

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Type
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


class Saves : AppCompatActivity() {

    var savesPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saves)
        loadSaves()
    }

    fun loadSaves() {
        val textView = findViewById<TextView>(R.id.tv_saves)
        val textView2 = findViewById<TextView>(R.id.tv_saves2)
        if (savesPath == "") {
            savesPath = this.getFilesDir().toString() + "/saves.json"
        }


        var jsonFile = File(savesPath)
        var jsonContent = ""
        Log.d("KUT_KOTLIN", jsonFile.exists().toString())

        if(!jsonFile.exists() || jsonFile.readText() == ""){
            Log.d("KUT_KOTLIN", "JSON niet gevonden, laad test JSON")
            jsonContent = loadJSONFromAsset("saves.json").toString()
            jsonFile.writeText(jsonContent)
        }else{
            jsonContent = jsonFile.readText()
//            Log.d("KUT_KOTLIN", jsonContent)
        }

//        textView2.text = jsonContent

        val gson = GsonBuilder().setDateFormat("dd-mm-yyyy hh:mm:ss").create()
        val collectionType: Type? = object : TypeToken<Collection<Save>>() {}.getType()
        val saves: Collection<Save>? = gson.fromJson(jsonContent, collectionType)

        if (saves != null) {
            val save = saves.elementAt(0)

            textView.text = "Op ${save.startdatum} startte ${save.spelers} met het spel ${save.speltype}"

            saves.elementAt(0).speltype = "Yahtzee"
            val jsonString:String = gson.toJson(saves)
            jsonFile.writeText(jsonString)
        }





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

