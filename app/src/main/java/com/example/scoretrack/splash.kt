package com.example.scoretrack

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val videoView = findViewById<VideoView>(R.id.videoViewSplash)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splashscreen

        videoView.setVideoPath(videoPath)
        videoView.start()

        videoView.setOnCompletionListener {
            val intent = Intent(this, Saves::class.java)
            startActivity(intent)
            finish()
        }

    }
}
