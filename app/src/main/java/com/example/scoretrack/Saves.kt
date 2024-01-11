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

    var savesPath = "saves.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saves)
        loadSaves()
    }

    fun loadSaves() {
        val textView = findViewById<TextView>(R.id.tv_saves)
        val textView2 = findViewById<TextView>(R.id.tv_saves2)

        var jsonFile = loadJSON(savesPath)
        jsonFile = null
        if(jsonFile == null){
            Log.d("KUT_KOTLIN", "JSON niet gevonden, laad test JSON")
            jsonFile = loadJSONFromAsset(savesPath)
        }else{
            Log.d("KUT_KOTLIN", jsonFile)
        }

        textView2.text = loadJSON(savesPath)


        val gson = GsonBuilder().setDateFormat("dd-mm-yyyy hh:mm:ss").create()

//        val save:Save = gson.fromJson(jsonFile, Save::class.java)
        val collectionType: Type? = object : TypeToken<Collection<Save>>() {}.getType()
        val saves: Collection<Save>? = gson.fromJson(jsonFile, collectionType)

//        val saves: Array<Save> = gson.fromJson(
//            jsonFile,
//            Array<Save>::class.java
//        )


        if (saves != null) {
            val save = saves.elementAt(0)
//            val sessies = save.saves

//            saves.elementAt(0).speltype = "Jatzee"
            textView.text = "Op ${save.startdatum} startte ${save.spelers} met het spel ${save.speltype}"

            saves.elementAt(0).speltype = "Jatzee shit"
            val jsonString:String = gson.toJson(saves)
            writeJSON(savesPath, jsonString)
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

    fun loadJSON(jsonPath: String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = openFileInput(jsonPath)
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

    fun writeJSON(jsonPath: String, newJSON: String) {
        Log.d("KUT_KOTLIN", "WERK NU")
        try {
            val file = File(jsonPath)
            file.delete()

            val os: OutputStream = openFileOutput("saves.json", MODE_APPEND)
            os.write(newJSON.toByteArray())
            os.close()
            Log.d("KUT_KOTLIN", "als je dit leest hoop ik dat die het fucking bestand heeft opgeslagen")
        } catch (ex: IOException) {
//            ex.printStackTrace()
            Log.d("KUT_KOTLIN", ex.stackTraceToString())
        }
    }
}

