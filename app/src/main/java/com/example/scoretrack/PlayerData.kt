package com.example.scoretrack

data class PlayerData(
    val playerNumber: String,
    val name: String,
    var currentStake: Int,
    var currentBet: Int = 0
)
