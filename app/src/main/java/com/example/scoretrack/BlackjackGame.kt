package com.example.scoretrack

import android.os.Bundle
import android.widget.ImageView
import android.view.View
import com.google.gson.Gson
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class BlackjackGame : AppCompatActivity() {

    private var playerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack_game)

        // Retrieve the number of players passed from the previous activity
        playerCount = intent.getIntExtra("playerCount", 0)

        // Add player views to the ConstraintLayout
        addPlayerViews(playerCount)
    }

    private fun addPlayerViews(playerCount: Int) {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainLayout)

        for (i in 0 until playerCount) {
            // Create ImageView for player icon
            val playerIcon = ImageView(this).apply {
                id = View.generateViewId()
                setImageResource(R.drawable.ic_player_icon) // Replace with your player icon
            }

            // Create TextView for player name
            val playerName = TextView(this).apply {
                id = View.generateViewId()
                text = "Player ${i + 1}"
            }

            // Add the views to the ConstraintLayout
            constraintLayout.addView(playerIcon)
            constraintLayout.addView(playerName)

            // Set constraints for the views
            setPlayerViewConstraints(playerIcon, playerName, constraintLayout, i)
        }
    }

    private fun setPlayerViewConstraints(playerIcon: ImageView, playerName: TextView, layout: ConstraintLayout, playerIndex: Int) {
        val set = ConstraintSet()
        set.clone(layout)

        // Example constraints - adjust as per your layout requirements
        // Player icon constraints
        set.connect(playerIcon.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 100 * playerIndex)
        set.connect(playerIcon.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

        // Player name constraints
        set.connect(playerName.id, ConstraintSet.TOP, playerIcon.id, ConstraintSet.BOTTOM, 8)
        set.connect(playerName.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

        set.applyTo(layout)
    }
}
