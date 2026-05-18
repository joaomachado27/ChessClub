package com.example.chessclub.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "partidas",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["whitePlayer"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["blackPlayer"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PartidaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val whitePlayer: String,
    val blackPlayer: String,
    val moves: String, // Armazenando como String formatada (ex: "1. d4 d5")
    val result: Int, // 1 = white won, 2 = black won, 3 = draw
    val timestamp: Long = System.currentTimeMillis()
)
