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

        // Initialize player views based on the number of players
        playerIcons = arrayOfNulls(players.size)
        playerNames = arrayOfNulls(players.size)
        playerStakes = arrayOfNulls(players.size)
        playerBets = arrayOfNulls(players.size)

        for (i in players.indices) {
            val player = players[i]
            val playerIndex = i + 1 // Player index starts from 1

            playerIcons[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerIcon$playerIndex", "id", packageName))
            playerNames[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerName$playerIndex", "id", packageName))
            playerStakes[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerStake$playerIndex", "id", packageName))
            playerBets[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerBet$playerIndex", "id", packageName))

            playerNames[i]?.text = player.name
            playerStakes[i]?.text = "Stake: ${player.currentStake}"
            playerBets[i]?.text = "Bet: 0"
        }

        for (i in players.size + 1..6) {
            val playerIconId = resources.getIdentifier("playerIcon$i", "id", packageName)
            val playerNameId = resources.getIdentifier("playerName$i", "id", packageName)
            val playerStakeId = resources.getIdentifier("playerStake$i", "id", packageName)
            val playerBetId = resources.getIdentifier("playerBet$i", "id", packageName)

            findViewById<ImageView>(playerIconId)?.visibility = View.GONE
            findViewById<TextView>(playerNameId)?.visibility = View.GONE
            findViewById<TextView>(playerStakeId)?.visibility = View.GONE
            findViewById<TextView>(playerBetId)?.visibility = View.GONE
        }
    }



    private fun addPlayerViews(constraintLayout: ConstraintLayout) {
        for (i in players.indices) {
            val player = players[i]
            val playerIndex = i + 1 // Player index starts from 1

            playerIcons[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerIcon$playerIndex", "id", packageName))
            playerNames[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerName$playerIndex", "id", packageName))
            playerStakes[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerStake$playerIndex", "id", packageName))
            playerBets[i] = constraintLayout.findViewById(
                resources.getIdentifier("playerBet$playerIndex", "id", packageName))

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
        showBetView.visibility = View.GONE
        resetBetButton.visibility = View.GONE
        nextPlayerButton.visibility = View.GONE
        startGameButton.visibility = View.GONE

        findViewById<ImageView>(R.id.chip5000).visibility = View.GONE
        findViewById<ImageView>(R.id.chip1000).visibility = View.GONE
        findViewById<ImageView>(R.id.chip500).visibility = View.GONE
        findViewById<ImageView>(R.id.chip200).visibility = View.GONE
        findViewById<ImageView>(R.id.chip100).visibility = View.GONE

        buttonBlackjack.visibility = View.VISIBLE
        buttonWin.visibility = View.VISIBLE
        buttonBust.visibility = View.VISIBLE
        buttonX2.visibility = View.VISIBLE

        currentPlayerIndex = 0
        startTurn(currentPlayerIndex)
    }


    private fun resetForNextRound() {
        players.forEach { player ->
            player.currentBet = 0
        }

        updatePlayerBetsUI()

        buttonBlackjack.visibility = View.GONE
        buttonWin.visibility = View.GONE
        buttonBust.visibility = View.GONE
        buttonX2.visibility = View.GONE

        findViewById<ImageView>(R.id.chip5000).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip1000).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip500).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip200).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.chip100).visibility = View.VISIBLE

        showBetView.visibility = View.VISIBLE
        resetBetButton.visibility = View.VISIBLE
        nextPlayerButton.visibility = View.VISIBLE

        startGameButton.visibility = View.GONE

        currentPlayerIndex = 0
        startTurn(currentPlayerIndex)

        Toast.makeText(this, "New round started. Place your bets!", Toast.LENGTH_SHORT).show()
    }


    private fun moveToNextPlayer() {
        currentPlayerIndex++
        if (currentPlayerIndex >= playerCount) {

            currentPlayerIndex = 0
            nextPlayerButton.visibility = View.GONE
            startGameButton.visibility = View.VISIBLE
        } else {
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

    private fun updatePlayerStake(player: PlayerData, outcome: String) {
        when (outcome) {
            "Win" -> player.currentStake += player.currentBet // Player wins the bet amount
            "Blackjack" -> player.currentStake += (player.currentBet * 1.5).toInt() // Blackjack pays 1.5 times
            "Bust" -> player.currentStake -= player.currentBet // Player loses the bet amount
            "X2" -> player.currentStake += player.currentBet * 2
        }
        updatePlayerStakesUI()
    }

    private fun processOutcome(outcome: String) {
        if (currentPlayerIndex in players.indices) {
            val currentPlayer = players[currentPlayerIndex]
            updateDealerStake(currentPlayer.currentBet, outcome)
            updatePlayerStake(currentPlayer, outcome)


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



