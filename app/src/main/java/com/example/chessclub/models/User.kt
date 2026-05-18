package com.example.chessclub.models

data class User(
    val username: String,
    val senha: String,
    val email: String = "",
    val vitorias: Int = 0,
    val empates: Int = 0,
    val derrotas: Int = 0
)