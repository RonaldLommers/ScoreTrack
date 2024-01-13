package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
    private lateinit var playerBets: Array<TextView?>
    private lateinit var showBetView: TextView
    private lateinit var resetBetButton: Button
    private var players: MutableList<PlayerData> = mutableListOf()
    private var playerCount = 0
    private var currentPlayerIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack_game)

        dealerStakeView = findViewById(R.id.dealerStakeView)
        showBetView = findViewById(R.id.showBetView)
        resetBetButton = findViewById(R.id.resetBetButton)
        updateDealerStakeUI()
        setupChipClickListeners()
        setupResetBetButtonListener()

        processIncomingGameData()
        initializePlayerViews()

        startTurn(currentPlayerIndex)
    }

    private fun setupResetBetButtonListener() {
        resetBetButton.setOnClickListener {
            resetCurrentPlayerBet()
        }
    }

    private fun resetCurrentPlayerBet() {
        if (currentPlayerIndex in players.indices) {
            val currentPlayer = players[currentPlayerIndex]
            currentPlayer.currentBet = 0
            showBetView.text = "Bet: 0"
            playerBets[currentPlayerIndex]?.text = "Bet: 0"
            Toast.makeText(this, "Bet reset to 0", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processIncomingGameData() {
        val gameDataJson = intent.getStringExtra("gameData")
        if (gameDataJson.isNullOrEmpty()) {
            showToastAndNavigateBack()
            return
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
    }

    private fun initializePlayerViews() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        playerIcons = arrayOfNulls(playerCount)
        playerNames = arrayOfNulls(playerCount)
        playerStakes = arrayOfNulls(playerCount)
        playerBets = arrayOfNulls(playerCount)

        addPlayerViews(constraintLayout)
        updatePlayerStakesUI()
    }

    private fun addPlayerViews(constraintLayout: ConstraintLayout) {
        for (i in 0 until playerCount) {
            val player = players[i]
            val playerIndex = i + 1
            playerIcons[i] = constraintLayout.findViewById(resources.getIdentifier("playerIcon$playerIndex", "id", packageName))
            playerNames[i] = constraintLayout.findViewById(resources.getIdentifier("playerName$playerIndex", "id", packageName))
            playerStakes[i] = constraintLayout.findViewById(resources.getIdentifier("playerStake$playerIndex", "id", packageName))
            playerBets[i] = constraintLayout.findViewById(resources.getIdentifier("playerBet$playerIndex", "id", packageName))

            playerNames[i]?.text = player.name
            playerStakes[i]?.text = "Stake: ${player.currentStake}"
            playerBets[i]?.text = "Bet: 0"
        }
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
            playerBets[currentPlayerIndex]?.text = "Bet: ${currentPlayer.currentBet}"
            Toast.makeText(this, "$amount added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid player turn", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePlayerStakesUI() {
        for (i in players.indices) {
            playerStakes[i]?.text = "Stake: ${players[i].currentStake}"
        }
    }

    private fun startTurn(playerIndex: Int) {
        highlightCurrentPlayerIcon(playerIndex)
    }

    private fun highlightCurrentPlayerIcon(playerIndex: Int) {
        for (i in players.indices) {
            val playerIcon = playerIcons[i]
            if (i == playerIndex) {
                playerIcon?.setImageResource(R.drawable.ic_player_icon_active)
            } else {
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
