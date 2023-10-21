package com.example.scoretrack

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.registerme.MyDBHelper
import com.example.registerme.register
import com.example.registerme.welcome

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnRegister: Button = findViewById(R.id.btRegister);
        btnRegister.setOnClickListener() {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

        var btnLogin: Button = findViewById(R.id.btLogin);
        btnLogin.setOnClickListener() {
            var helper = MyDBHelper(applicationContext)
            var cr: Cursor;

            var editTextMail: EditText = findViewById(R.id.etMail);
            var editTextPassword: EditText = findViewById(R.id.etPassword);


            cr = helper.loginCheck(editTextMail.text.toString(), editTextPassword.text.toString());

            if (cr.getCount() > 0) {
                val intent = Intent(this, welcome::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Logingegevens zijn niet correct!", Toast.LENGTH_LONG).show()
            }
        }
    }
}