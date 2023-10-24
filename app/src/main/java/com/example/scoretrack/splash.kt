package com.example.scoretrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var iv: ImageView = findViewById(R.id.logoscoretrack)
        iv.alpha = 0f;
        iv.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this,register::class.java)
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }


    }
}