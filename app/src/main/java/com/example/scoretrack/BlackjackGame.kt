package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.gson.Gson

class BlackjackGame : AppCompatActivity() {

    private lateinit var playerIcons: Array<ImageView?>
    private lateinit var playerNames: Array<TextView?>
    private lateinit var playerStakes: Array<TextView?>
    private var playerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack_game)

        val gameDataJson = intent.getStringExtra("gameData")
        if (gameDataJson.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
        }

        val gameData = try {
            Gson().fromJson(gameDataJson, GameData::class.java)
        } catch (e: Exception) {
            showToastAndNavigateBack()
            return
        }

        if (gameData.players.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
        }

        playerCount = gameData.players.size

        // Initialize arrays for views
        playerIcons = arrayOfNulls(playerCount)
        playerNames = arrayOfNulls(playerCount)
        playerStakes = arrayOfNulls(playerCount)

        // Add player views to the ConstraintLayout
        addPlayerViews()
    }

    private fun addPlayerViews() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainLayout)

        for (i in 0 until playerCount) {
            // Create and add ImageView for player icon
            playerIcons[i] = ImageView(this).apply {
                id = View.generateViewId()
                setImageResource(R.drawable.ic_player_icon) // Replace with your player icon
                constraintLayout.addView(this)
            }

            // Create and add TextView for player name
            playerNames[i] = TextView(this).apply {
                id = View.generateViewId()
                text = "Player ${i + 1}" // Example, replace with actual player name
                constraintLayout.addView(this)
            }

            // Create and add TextView for player stake
            playerStakes[i] = TextView(this).apply {
                id = View.generateViewId()
                text = "Stake: 0" // Example, replace with actual player stake
                constraintLayout.addView(this)
            }

            // Set constraints for the views
            setPlayerViewConstraints(playerIcons[i]!!, playerNames[i]!!, playerStakes[i]!!, constraintLayout, i)
        }
    }

    private fun setPlayerViewConstraints(playerIcon: ImageView, playerName: TextView, playerStake: TextView, layout: ConstraintLayout, playerIndex: Int) {
        val set = ConstraintSet()
        set.clone(layout)

        // Example constraints - adjust as per your layout requirements
        // Player icon constraints
        set.connect(playerIcon.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 100 * playerIndex)
        set.connect(playerIcon.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

        // Player name constraints
        set.connect(playerName.id, ConstraintSet.TOP, playerIcon.id, ConstraintSet.BOTTOM, 8)
        set.connect(playerName.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

        // Player stake constraints
        set.connect(playerStake.id, ConstraintSet.TOP, playerName.id, ConstraintSet.BOTTOM, 4)
        set.connect(playerStake.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

        set.applyTo(layout)
    }

    private fun showToastAndNavigateBack() {
        Toast.makeText(this, "Data not found, please try again", Toast.LENGTH_LONG).show()
        navigateBackToBlackjackActivity()
    }

    private fun navigateBackToBlackjackActivity() {
        val intent = Intent(this, Blackjack::class.java)
        startActivity(intent)
        finish() // Close the current activity
    }
}
