package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var nextPlayerButton: Button
    private lateinit var startGameButton: Button
    private lateinit var buttonBlackjack: ImageView
    private lateinit var buttonWin: ImageView
    private lateinit var buttonBust: ImageView
    private lateinit var buttonX2: ImageView
    private var players: MutableList<PlayerData> = mutableListOf()
    private var playerCount = 0
    private var currentPlayerIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack_game)

        dealerStakeView = findViewById(R.id.dealerStakeView)
        showBetView = findViewById(R.id.showBetView)
        resetBetButton = findViewById(R.id.resetBetButton)
        nextPlayerButton = findViewById(R.id.nextPlayerButton)
        startGameButton = findViewById(R.id.startGameButton)
        buttonBlackjack = findViewById(R.id.buttonBlackjack)
        buttonWin = findViewById(R.id.buttonWin)
        buttonBust = findViewById(R.id.buttonBust)
        buttonX2 = findViewById(R.id.buttonX2)

        updateDealerStakeUI()
        setupChipClickListeners()
        setupResetBetButtonListener()
        setupNextPlayerButtonListener()
        setupStartGameButtonListener()
        setupOutcomeButtons()

        processIncomingGameData()
        initializePlayerViews()

        startTurn(currentPlayerIndex)
    }

    private fun setupChipClickListeners() {
        findViewById<ImageView>(R.id.chip100).setOnClickListener { increaseBet(100) }
        findViewById<ImageView>(R.id.chip200).setOnClickListener { increaseBet(200) }
        findViewById<ImageView>(R.id.chip500).setOnClickListener { increaseBet(500) }
        findViewById<ImageView>(R.id.chip1000).setOnClickListener { increaseBet(1000) }
        findViewById<ImageView>(R.id.chip5000).setOnClickListener { increaseBet(5000) }
    }

    private fun setupResetBetButtonListener() {
        resetBetButton.setOnClickListener {
            resetCurrentPlayerBet()
        }
    }

    private fun setupNextPlayerButtonListener() {
        nextPlayerButton.setOnClickListener {
            moveToNextPlayer()
        }
    }

    private fun setupStartGameButtonListener() {
        startGameButton.setOnClickListener {
            startGame()
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
            playerIcons[i] = constraintLayout.findViewById(
                resources.getIdentifier(
                    "playerIcon$playerIndex",
                    "id",
                    packageName
                )
            )
            playerNames[i] = constraintLayout.findViewById(
                resources.getIdentifier(
                    "playerName$playerIndex",
                    "id",
                    packageName
                )
            )
            playerStakes[i] = constraintLayout.findViewById(
                resources.getIdentifier(
                    "playerStake$playerIndex",
                    "id",
                    packageName
                )
            )
            playerBets[i] = constraintLayout.findViewById(
                resources.getIdentifier(
                    "playerBet$playerIndex",
                    "id",
                    packageName
                )
            )

            playerNames[i]?.text = player.name
            playerStakes[i]?.text = "Stake: ${player.currentStake}"
            playerBets[i]?.text = "Bet: 0"
        }
    }

    private fun updatePlayerStakesUI() {
        for (i in players.indices) {
            playerStakes[i]?.text = "Stake: ${players[i].currentStake}"
        }
    }

    private fun updatePlayerBetsUI() {
        // Update the UI for player bets
        playerBets.forEachIndexed { index, textView ->
            textView?.text = "Bet: ${players[index].currentBet}"
        }
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

    private fun resetCurrentPlayerBet() {
        if (currentPlayerIndex in players.indices) {
            val currentPlayer = players[currentPlayerIndex]
            currentPlayer.currentBet = 0
            showBetView.text = "Bet: 0"
            playerBets[currentPlayerIndex]?.text = "Bet: 0"
            Toast.makeText(this, "Bet reset to 0", Toast.LENGTH_SHORT).show()
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

    private fun startGame() {
        // Hide betting interface
        showBetView.visibility = View.GONE
        resetBetButton.visibility = View.GONE
        nextPlayerButton.visibility = View.GONE  // Hide next button during the game

        // Hide betting chips
        findViewById<ImageView>(R.id.chip5000).visibility = View.GONE
        findViewById<ImageView>(R.id.chip1000).visibility = View.GONE
        findViewById<ImageView>(R.id.chip500).visibility = View.GONE
        findViewById<ImageView>(R.id.chip200).visibility = View.GONE
        findViewById<ImageView>(R.id.chip100).visibility = View.GONE

        // Show outcome buttons for game actions
        buttonBlackjack.visibility = View.VISIBLE
        buttonWin.visibility = View.VISIBLE
        buttonBust.visibility = View.VISIBLE
        buttonX2.visibility = View.VISIBLE

        // Initialize game state
        // Example: Set currentPlayerIndex to 0 to start with the first player
        currentPlayerIndex = 0
        startTurn(currentPlayerIndex)
        // Additional game initialization logic here
    }


    private fun resetForNextRound() {
        // Logic to reset the game for a new round

        // Reset each player's bet to zero for the new round
        players.forEach { player ->
            player.currentBet = 0
        }

        // Update the UI to reflect the reset bets
        updatePlayerBetsUI()

        // Hide the outcome buttons as they are only needed during game play
        buttonBlackjack.visibility = View.GONE
        buttonWin.visibility = View.GONE
        buttonBust.visibility = View.GONE
        buttonX2.visibility = View.GONE

        // Show the betting chips for the new round
        findViewById<ImageView>(R.id.chip5000).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip1000).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip500).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip200).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip100).visibility = View.VISIBLE

        // Make the betting interface visible again
        showBetView.visibility = View.VISIBLE
        resetBetButton.visibility = View.VISIBLE

        // Since we are resetting for a new round, hide the 'Start Game' button
        startGameButton.visibility = View.GONE

        // Reset the currentPlayerIndex to start the new round from the first player
        currentPlayerIndex = 0

        // Optionally, you can show a toast message or update the UI to indicate a new round
        Toast.makeText(this, "New round started. Place your bets!", Toast.LENGTH_SHORT).show()
    }

    private fun moveToNextPlayer() {
        currentPlayerIndex++
        if (currentPlayerIndex >= playerCount) {
            // If all players have taken their turn, show the "Start" button for the game
            currentPlayerIndex = 0
            nextPlayerButton.visibility = View.GONE
            startGameButton.visibility = View.VISIBLE
        } else {
            // Otherwise, continue with the next player's turn
            startTurn(currentPlayerIndex)
        }
    }

    private fun setupOutcomeButtons() {
        buttonBlackjack.setOnClickListener {
            Log.d("BlackjackGame", "Blackjack button clicked")
            processOutcome("Blackjack")
        }
        buttonWin.setOnClickListener {
            Log.d("BlackjackGame", "Win button clicked")
            processOutcome("Win")
        }
        buttonBust.setOnClickListener {
            Log.d("BlackjackGame", "Bust button clicked")
            processOutcome("Bust")
        }
        buttonX2.setOnClickListener {
            Log.d("BlackjackGame", "X2 button clicked")
            processOutcome("X2")
        }
    }

    private fun processOutcome(outcome: String) {
        if (currentPlayerIndex in players.indices) {
            val currentPlayer = players[currentPlayerIndex]
            updateDealerStake(currentPlayer.currentBet, outcome)

            // Log for testing
            Log.d("BlackjackGame", "Processing outcome: $outcome for player $currentPlayerIndex")

            // Move to the next player or reset if at the end
            currentPlayerIndex++
            if (currentPlayerIndex >= playerCount) {
                currentPlayerIndex = 0
                resetForNextRound()
            } else {
                startTurn(currentPlayerIndex)
            }
        }
    }

    private fun updateDealerStake(playerBet: Int, outcome: String) {
        when (outcome) {
            "Bust" -> dealerStake += playerBet
            "Win" -> dealerStake -= playerBet
            "Blackjack" -> dealerStake -= (playerBet * 1.5).toInt()
            "X2" -> dealerStake -= playerBet * 2
        }
        updateDealerStakeUI()
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
}



