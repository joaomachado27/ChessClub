package com.example.chessclub.data

import com.example.chessclub.database.dao.PartidaDAO
import com.example.chessclub.database.entities.PartidaEntity
import com.example.chessclub.models.Partida
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PartidaRepository {
    suspend fun createPartida(partida: Partida): Boolean
    fun getPartidasByUser(username: String): Flow<List<Partida>>
}

class PartidaRepositoryImpl(private val partidaDAO: PartidaDAO) : PartidaRepository {

    override suspend fun createPartida(partida: Partida): Boolean {
        return try {
            partidaDAO.insertWithStats(
                PartidaEntity(
                    whitePlayer = partida.whitePlayer,
                    blackPlayer = partida.blackPlayer,
                    moves = partida.moves,
                    result = partida.result,
                    timestamp = partida.timestamp
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getPartidasByUser(username: String): Flow<List<Partida>> {
        return partidaDAO.getPartidasByUser(username).map { entities ->
            entities.map { entity ->
                Partida(
                    id = entity.id,
                    whitePlayer = entity.whitePlayer,
                    blackPlayer = entity.blackPlayer,
                    moves = entity.moves,
                    result = entity.result,
                    timestamp = entity.timestamp
                )
            }
        }
    }
}
