package com.example.scoretrack

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.scoretrack.R

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        var btnRegister: Button = findViewById(R.id.btRegister);

        btnRegister.setOnClickListener {
            insertRegister();
        }

    }

    fun insertRegister(){
        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM USERS", null)
        var cv = ContentValues()

        var editTextName: EditText = findViewById(R.id.etName);
        var editTextSurname: EditText = findViewById(R.id.etSurname);
        var editTextMail: EditText = findViewById(R.id.etMail);
        var editTextPassword: EditText = findViewById(R.id.etPassword);

        cv.put("NAME", editTextName.text.toString())
        cv.put("SURNAME", editTextSurname.text.toString())
        cv.put("EMAIL", editTextMail.text.toString())
        cv.put("PWD", editTextPassword.text.toString())

        db.insert("USERS",null,cv)

        editTextName.setText("")
        editTextSurname.setText("")
        editTextMail.setText("")
        editTextPassword.setText("")

        editTextName.requestFocus()
    }
}