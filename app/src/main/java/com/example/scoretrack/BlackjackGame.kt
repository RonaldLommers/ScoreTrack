package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.gson.Gson

class BlackjackGame : AppCompatActivity() {

    private val initialDealerStake = 200000
    private var dealerStake = initialDealerStake

    private lateinit var dealerStakeView: TextView // TextView for displaying dealer's stake
    private lateinit var playerIcons: Array<ImageView?>
    private lateinit var playerNames: Array<TextView?>
    private lateinit var playerStakes: Array<TextView?>
    private var players: MutableList<PlayerData> = mutableListOf()
    private var playerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack_game)

        dealerStakeView = findViewById(R.id.dealerStakeView)
        // Ensure you have this TextView in your layout
        updateDealerStakeUI() // Initial update of dealer's stake in UI

        val gameDataJson = intent.getStringExtra("gameData")
        if (gameDataJson.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
        } else {
            // For debugging, you can log the received JSON
            Log.d("BlackjackGame", "Received game data: $gameDataJson")
        }

        val gameData = try {
            Gson().fromJson(gameDataJson, GameData::class.java)
        } catch (e: Exception) {
            e.printStackTrace() // Log the exception for debugging
            showToastAndNavigateBack()
            return
        }

        if (gameData.players.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
        }

        players = gameData.players.toMutableList()
        playerCount = players.size

        // Initialize arrays for views
        playerIcons = arrayOfNulls(playerCount)
        playerNames = arrayOfNulls(playerCount)
        playerStakes = arrayOfNulls(playerCount)

        // Add player views to the ConstraintLayout
        addPlayerViews()
        updatePlayerStakesUI()
    }

    private fun addPlayerViews() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainLayout)

        val maxPlayersToShow = 6
        val loopLimit = minOf(playerCount, maxPlayersToShow) // Limit the loop to 6 or less players

        for (i in 0 until loopLimit) {
            val player = players[i]

            // Create and add ImageView for player icon
            playerIcons[i] = ImageView(this).apply {
                id = View.generateViewId()
                setImageResource(R.drawable.ic_player_icon)
                constraintLayout.addView(this)
            }

            // Create and add TextView for player name
            playerNames[i] = TextView(this).apply {
                id = View.generateViewId()
                text = player.name
                constraintLayout.addView(this)
            }

            // Create and add TextView for player stake
            playerStakes[i] = TextView(this).apply {
                id = View.generateViewId()
                text = "Stake: ${player.currentStake}"
                constraintLayout.addView(this)
            }

            setPlayerViewConstraints(playerIcons[i]!!, playerNames[i]!!, playerStakes[i]!!, constraintLayout, i)
        }
    }

    // Add this new function to handle player outcomes and update stakes
    private fun handleOutcome(playerNumber: String, playerBet: Int, outcome: String) {
        val player = players.find { it.playerNumber == playerNumber }

        player?.let {
            when (outcome) {
                "Bust" -> {
                    dealerStake += playerBet
                    it.currentStake -= playerBet
                }
                "Win" -> {
                    dealerStake -= playerBet
                    it.currentStake += playerBet
                }
                "Blackjack" -> {
                    val winnings = (playerBet * 1.5).toInt()
                    dealerStake -= winnings
                    it.currentStake += winnings
                }
                "X2" -> {
                    val winnings = playerBet * 2
                    dealerStake -= winnings
                    it.currentStake += winnings
                }
            }
            updatePlayerStakesUI()
            updateDealerStakeUI()
        } ?: Toast.makeText(this, "Player not found", Toast.LENGTH_SHORT).show()
    }

    private fun updatePlayerStakesUI() {
        for (i in players.indices) {
            playerStakes[i]?.text = "Stake: ${players[i].currentStake}"
        }
    }

    private fun updateDealerStakeUI() {
        dealerStakeView.text = "Dealer Stake: $dealerStake"
    }

    private fun setPlayerViewConstraints(playerIcon: ImageView, playerName: TextView, playerStake: TextView, layout: ConstraintLayout, playerIndex: Int) {
        val set = ConstraintSet()
        set.clone(layout)

        set.connect(playerIcon.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 100 * playerIndex)
        set.connect(playerIcon.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

        set.connect(playerName.id, ConstraintSet.TOP, playerIcon.id, ConstraintSet.BOTTOM, 8)
        set.connect(playerName.id, ConstraintSet.START, layout.id, ConstraintSet.START, 16)

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
