package com.example.chessclub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.chessclub.database.entities.PartidaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDAO {
    @Insert
    suspend fun insertOnly(partida: PartidaEntity)

    @Query("UPDATE users SET v = v + 1 WHERE username = :username")
    suspend fun incrementWin(username: String)

    @Query("UPDATE users SET e = e + 1 WHERE username = :username")
    suspend fun incrementDraw(username: String)

    @Query("UPDATE users SET d = d + 1 WHERE username = :username")
    suspend fun incrementLoss(username: String)

    @Transaction
    suspend fun insertWithStats(partida: PartidaEntity) {
        insertOnly(partida)
        when (partida.result) {
            1 -> { // White won
                incrementWin(partida.whitePlayer)
                incrementLoss(partida.blackPlayer)
            }
            2 -> { // Black won
                incrementWin(partida.blackPlayer)
                incrementLoss(partida.whitePlayer)
            }
            3 -> { // Draw
                incrementDraw(partida.whitePlayer)
                incrementDraw(partida.blackPlayer)
            }
        }
    }

    @Query("SELECT * FROM partidas ORDER BY timestamp DESC")
    fun getAllPartidas(): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM partidas WHERE whitePlayer = :username OR blackPlayer = :username ORDER BY timestamp DESC")
    fun getPartidasByUser(username: String): Flow<List<PartidaEntity>>
}
