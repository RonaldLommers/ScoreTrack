package com.example.scoretrack

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val btnRegister: Button = findViewById(R.id.btRegister);

        btnRegister.setOnClickListener {
            insertRegister();
        }

    }

    @SuppressLint("Recycle")
    fun insertRegister(){
        val helper = MyDBHelper(applicationContext)
        val db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM USERS", null)
        val cv = ContentValues()

        val editTextName: EditText = findViewById(R.id.etName);
        val editTextSurname: EditText = findViewById(R.id.etSurname);
        val editTextMail: EditText = findViewById(R.id.etMail);
        val editTextPassword: EditText = findViewById(R.id.etPassword);

        cv.put("NAME", editTextName.text.toString())
        cv.put("SURNAME", editTextSurname.text.toString())
        cv.put("EMAIL", editTextMail.text.toString())
        cv.put("PWD", editTextPassword.text.toString())

        db.insert("USERS",null,cv)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}