package com.example.scoretrack

import androidx.appcompat.app.AppCompatActivity
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
                val gameName = gameNameEditText.text.toString()
                // Use the gameName for your game logic
                // Start the game
            } else {
                val message = if (playerCount == 0) {
                    "Add at least 1 player"
                } else {
                    "Please enter a game name and ensure all players have a name and stake"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
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
            hint = "Stake"
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
}
