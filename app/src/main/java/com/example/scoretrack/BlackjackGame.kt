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
        updateDealerStakeUI()

        val gameDataJson = intent.getStringExtra("gameData")
        if (gameDataJson.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
        } else {
            Log.d("BlackjackGame", "Received game data: $gameDataJson")
        }

        val gameData = try {
            Gson().fromJson(gameDataJson, GameData::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            showToastAndNavigateBack()
            return
        }

        if (gameData.players.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
        }

        players = gameData.players.toMutableList()
        playerCount = players.size

        playerIcons = arrayOfNulls(playerCount)
        playerNames = arrayOfNulls(playerCount)
        playerStakes = arrayOfNulls(playerCount)

        addPlayerViews()
        updatePlayerStakesUI()
    }

    private fun addPlayerViews() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        val maxPlayerSlots = 6

        for (i in 1..maxPlayerSlots) {
            constraintLayout.findViewById<ImageView>(resources.getIdentifier("playerIcon$i", "id", packageName))?.visibility = View.GONE
            constraintLayout.findViewById<TextView>(resources.getIdentifier("playerName$i", "id", packageName))?.visibility = View.GONE
            constraintLayout.findViewById<TextView>(resources.getIdentifier("playerStake$i", "id", packageName))?.visibility = View.GONE
        }

        for (i in 0 until playerCount) {
            val player = players[i]
            val playerIconId = resources.getIdentifier("playerIcon${i + 1}", "id", packageName)
            var playerIcon = constraintLayout.findViewById<ImageView>(playerIconId)
            if (playerIcon == null) {
                playerIcon = ImageView(this).apply {
                    id = View.generateViewId()
                    setImageResource(R.drawable.ic_player_icon)
                }
                constraintLayout.addView(playerIcon)
            } else {
                playerIcon.visibility = View.VISIBLE
            }
            playerIcons[i] = playerIcon

            val playerNameId = resources.getIdentifier("playerName${i + 1}", "id", packageName)
            var playerName = constraintLayout.findViewById<TextView>(playerNameId)
            if (playerName == null) {
                playerName = TextView(this).apply {
                    id = View.generateViewId()
                    text = player.name
                }
                constraintLayout.addView(playerName)
            } else {
                playerName.text = player.name
                playerName.visibility = View.VISIBLE
            }
            playerNames[i] = playerName

            val playerStakeId = resources.getIdentifier("playerStake${i + 1}", "id", packageName)
            var playerStake = constraintLayout.findViewById<TextView>(playerStakeId)
            if (playerStake == null) {
                playerStake = TextView(this).apply {
                    id = View.generateViewId()
                    text = "Stake: ${player.currentStake}"
                }
                constraintLayout.addView(playerStake)
            } else {
                playerStake.text = "Stake: ${player.currentStake}"
                playerStake.visibility = View.VISIBLE
            }
            playerStakes[i] = playerStake

            // Set constraints for the views
            setPlayerViewConstraints(playerIcon, playerName, playerStake, constraintLayout, i)
        }
    }

    private fun setPlayerViewConstraints(playerIcon: ImageView, playerName: TextView, playerStake: TextView, layout: ConstraintLayout, playerIndex: Int) {
        val set = ConstraintSet()
        set.clone(layout)

        // For a single player on each side
        if (playerCount <= 2) {
            val isLeftSide = playerIndex == 0
            val topMargin = if (isLeftSide) 60 else 204 // Top margin for left or right side
            val startGuidelineId = if (isLeftSide) R.id.leftGuideline else R.id.rightGuideline
            val iconMarginStart = 32

            // Connect player icon
            set.connect(playerIcon.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, topMargin)
            set.connect(playerIcon.id, ConstraintSet.START, startGuidelineId, ConstraintSet.START, iconMarginStart)

            // Connect player name
            set.connect(playerName.id, ConstraintSet.TOP, playerIcon.id, ConstraintSet.BOTTOM, 8)
            set.connect(playerName.id, ConstraintSet.START, playerIcon.id, ConstraintSet.START, 0)

            // Connect player stake
            set.connect(playerStake.id, ConstraintSet.TOP, playerName.id, ConstraintSet.BOTTOM, 4)
            set.connect(playerStake.id, ConstraintSet.START, playerName.id, ConstraintSet.START, 0)
        } else {
            // Constraints for multiple players
            // ... (existing logic for multiple players)
        }

        set.applyTo(layout)
    }


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

        // Set constraints for player views here. This will vary based on your layout.
    }

    private fun showToastAndNavigateBack() {
        Toast.makeText(this, "Data not found, please try again", Toast.LENGTH_LONG).show()
        navigateBackToBlackjackActivity()
    }

    private fun navigateBackToBlackjackActivity() {
        val intent = Intent(this, Blackjack::class.java)
        startActivity(intent)
        finish()
    }
}
