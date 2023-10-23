package com.example.scoretrack

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRegister: Button = findViewById(R.id.btRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

        val btnLogin: Button = findViewById(R.id.btLogin)
        btnLogin.setOnClickListener {
            val helper = MyDBHelper(applicationContext)
            val cr: Cursor

            val editTextMail: EditText = findViewById(R.id.etMail)
            val editTextPassword: EditText = findViewById(R.id.etPassword)


            cr = helper.loginCheck(editTextMail.text.toString(), editTextPassword.text.toString())

            if (cr.count > 0) {
                val intent = Intent(this, welcome::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Logingegevens zijn niet correct!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}