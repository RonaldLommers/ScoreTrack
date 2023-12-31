package com.example.scoretrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.LinearLayout
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView


class Blackjack : AppCompatActivity() {
    private var playerCount = 0
    private val maxPlayers = 6
    private lateinit var playersLayout: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack)

        playersLayout = findViewById(R.id.playersLayout) // Define this in your XML

        val addPlayerButton = findViewById<Button>(R.id.addPlayerButton)
        addPlayerButton.setOnClickListener {
            if (playerCount < maxPlayers) {
                addPlayer()
            }
        }
    }

    private fun addPlayer() {
        val playerView = createPlayerView(playerCount + 1)
        playersLayout.addView(playerView)
        playerCount++

        // Adjust the layout based on the number of players
        adjustLayoutForPlayers()
    }

    private fun createPlayerView(playerNumber: Int): View {
        val playerView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val playerIcon = ImageView(this).apply {
            // Set your drawable resource here
            setImageResource(R.drawable.ic_player_icon)
            layoutParams = LinearLayout.LayoutParams(100, 100) // Set your desired size
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

    private fun adjustLayoutForPlayers() {
        // Assuming playersLayout is a LinearLayout
        val layout = findViewById<LinearLayout>(R.id.playersLayout)

        // Clear all views to rearrange them
        layout.removeAllViews()

        // Create a new layout structure based on the number of players
        when (playerCount) {
            1 -> {
                // For 1 player, add the single view to the center
                layout.addView(createPlayerView(1))
            }
            2 -> {
                // For 2 players, add them in a single row
                val row = createRowLayout()
                for (i in 1..2) {
                    row.addView(createPlayerView(i))
                }
                layout.addView(row)
            }
            3 -> {
                // For 3 players, 2 in the first row, 1 centered in the second row
                val row1 = createRowLayout()
                for (i in 1..2) {
                    row1.addView(createPlayerView(i))
                }
                layout.addView(row1)

                val row2 = createRowLayout()
                row2.addView(createPlayerView(3))
                layout.addView(row2)
            }
            // Similar logic for 4, 5, and 6 players...
        }
    }

    private fun createRowLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }
}
