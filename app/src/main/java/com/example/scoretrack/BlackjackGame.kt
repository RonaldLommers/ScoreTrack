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
import com.google.gson.Gson

class BlackjackGame : AppCompatActivity() {

    private val initialDealerStake = 200000
    private var dealerStake = initialDealerStake

    private lateinit var dealerStakeView: TextView
    private lateinit var playerIcons: Array<ImageView?>
    private lateinit var playerNames: Array<TextView?>
    private lateinit var playerStakes: Array<TextView?>
    private var players: MutableList<PlayerData> = mutableListOf()
    private var playerCount = 0
    private var currentPlayerIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack_game)

        dealerStakeView = findViewById(R.id.dealerStakeView)
        showBetView = findViewById(R.id.showBetView) // Assuming this is your TextView ID
        updateDealerStakeUI()
        setupChipClickListeners()

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
        startTurn(currentPlayerIndex)
    }

    private fun setupChipClickListeners() {
        findViewById<ImageView>(R.id.chip100).setOnClickListener { increaseBet(100) }
        findViewById<ImageView>(R.id.chip200).setOnClickListener { increaseBet(200) }
        findViewById<ImageView>(R.id.chip500).setOnClickListener { increaseBet(500) }
        findViewById<ImageView>(R.id.chip1000).setOnClickListener { increaseBet(1000) }
        findViewById<ImageView>(R.id.chip5000).setOnClickListener { increaseBet(5000) }
    }

    private fun increaseBet(amount: Int) {
        if (currentPlayerIndex in players.indices) {
            val currentPlayer = players[currentPlayerIndex]
            currentPlayer.currentBet += amount
            showBetView.text = "Bet: ${currentPlayer.currentBet}"
            Toast.makeText(this, "$amount is added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid player turn", Toast.LENGTH_SHORT).show()
        }
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
        }
    }

    private fun updatePlayerStakesUI() {
        for (i in players.indices) {
            playerStakes[i]?.text = "Stake: ${players[i].currentStake}"
        }
    }

    private fun startTurn(playerIndex: Int) {
        highlightCurrentPlayerIcon(playerIndex)
        // Handle player's turn logic here
    }

    private fun highlightCurrentPlayerIcon(playerIndex: Int) {
        for (i in players.indices) {
            val playerIcon = playerIcons[i]
            if (i == playerIndex) {
                // Set to active icon
                playerIcon?.setImageResource(R.drawable.ic_player_icon_active)
            } else {
                // Set to regular icon
                playerIcon?.setImageResource(R.drawable.ic_player_icon)
            }
        }
    }

    private fun updateDealerStakeUI() {
        dealerStakeView.text = "Dealer Stake: $dealerStake"
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

    // Add more game logic and methods as needed
}
