package com.example.chessclub.models

data class Partida(
    val id: Long = 0,
    val whitePlayer: String,
    val blackPlayer: String,
    val moves: String,
    val result: Int, // 1 = white won, 2 = black won, 3 = draw
    val timestamp: Long = System.currentTimeMillis()
)
