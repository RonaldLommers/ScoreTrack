package com.example.scoretrack

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.File

class Blackjack : AppCompatActivity() {
    private var playerCount = 0
    private val maxPlayers = 6
    private lateinit var playersLayout: LinearLayout
    private lateinit var gameNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack)

        playersLayout = findViewById(R.id.playersLayout)
        gameNameEditText = findViewById(R.id.gameNameEditText)

        val addPlayerButton = findViewById<Button>(R.id.addPlayerButton)
        addPlayerButton.setOnClickListener {
            if (playerCount < maxPlayers) {
                addPlayer()
            }
        }

        val startGameButton = findViewById<Button>(R.id.startGameButton)
        startGameButton.setOnClickListener {
            if (playerCount > 0 && isGameInfoComplete()) {
                val jsonData = serializeGameData()
                saveGameData(jsonData)
                Toast.makeText(this, "Game data saved", Toast.LENGTH_SHORT).show()

                // Create an Intent to start BlackjackGame activity
                val intent = Intent(this, BlackjackGame::class.java)
                // Pass the serialized game data
                intent.putExtra("gameData", jsonData)
                startActivity(intent)
            } else {
                val message = if (playerCount == 0) {
                    "Add at least 1 player"
                } else {
                    "Please enter a game name and ensure all players have a name and stake"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
        loadGameIfAvailable()
    }

    private fun addPlayer() {
        if (playerCount % 2 == 0) {
            val row = createRowLayout()
            playersLayout.addView(row)
        }

        val lastRow = playersLayout.getChildAt(playersLayout.childCount - 1) as LinearLayout
        val playerView = createPlayerView(playerCount + 1)
        lastRow.addView(playerView)
        playerCount++
    }

    private fun createRowLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
        }
    }

    private fun createPlayerView(playerNumber: Int): View {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val playerViewWidth = metrics.widthPixels / 2

        val playerView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                playerViewWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val playerIcon = ImageView(this).apply {
            setImageResource(R.drawable.ic_player_icon)
            layoutParams = LinearLayout.LayoutParams(
                100, 100
            )
            setOnClickListener {
                removePlayer(playerView)
            }
        }
        playerView.addView(playerIcon)

        val playerName = EditText(this).apply {
            hint = "Player $playerNumber Name"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        playerView.addView(playerName)

        val playerStake = EditText(this).apply {
            hint = "currentStake"
            inputType = InputType.TYPE_CLASS_NUMBER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        playerView.addView(playerStake)

        return playerView
    }

    private fun removePlayer(playerView: View) {
        val row = playerView.parent as LinearLayout
        row.removeView(playerView)
        playerCount--
        Toast.makeText(this, "Player Removed", Toast.LENGTH_SHORT).show()
    }

    private fun isPlayerInfoComplete(): Boolean {
        for (i in 0 until playersLayout.childCount) {
            val row = playersLayout.getChildAt(i) as LinearLayout
            for (j in 0 until row.childCount) {
                val playerView = row.getChildAt(j) as LinearLayout
                val playerName = playerView.getChildAt(1) as EditText
                val playerStake = playerView.getChildAt(2) as EditText

                if (playerName.text.toString().trim().isEmpty() || playerStake.text.toString().trim().isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    private fun isGameInfoComplete(): Boolean {
        if (gameNameEditText.text.toString().trim().isEmpty()) {
            return false
        }
        return isPlayerInfoComplete()
    }

    private fun serializeGameData(): String {
        val players = mutableListOf<PlayerData>()
        for (i in 0 until playersLayout.childCount) {
            val row = playersLayout.getChildAt(i) as LinearLayout
            for (j in 0 until row.childCount) {
                val playerView = row.getChildAt(j) as LinearLayout
                val playerName = (playerView.getChildAt(1) as EditText).text.toString()
                val playerStakeString = (playerView.getChildAt(2) as EditText).text.toString()

                // Convert playerStakeString to Int, default to 0 if it's not a valid number
                val playerStake = playerStakeString.toIntOrNull() ?: 0

                // Now we pass both name and stake when creating PlayerData
                players.add(PlayerData(playerName, playerStake))
            }
        }
        val gameData = GameData(gameNameEditText.text.toString(), players)
        return Gson().toJson(gameData)
    }


    private fun saveGameData(jsonData: String) {
        val file = File(filesDir, "gameData.json")
        file.writeText(jsonData)
    }

    private fun loadGameData(): String {
        val file = File(filesDir, "gameData.json")
        if (file.exists()) {
            return file.readText()
        }
        return ""
    }

    private fun loadGameIfAvailable() {
        val jsonData = loadGameData()
        if (jsonData.isNotEmpty()) {
            val gameData = Gson().fromJson(jsonData, GameData::class.java)
            gameNameEditText.setText(gameData.gameName)
            // TODO: Add logic to restore each player's data to the UI
        }
    }
}
