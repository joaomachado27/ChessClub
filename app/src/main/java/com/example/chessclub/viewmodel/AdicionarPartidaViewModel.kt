package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessclub.data.PartidaRepository
import com.example.chessclub.models.Partida
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AdicionarPartidaViewModel(private val repository: PartidaRepository) : ViewModel() {

    private val _saveSuccess = MutableSharedFlow<Boolean>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun salvarPartida(white: String, black: String, moves: String, result: Int) {
        viewModelScope.launch {
            if (white.isBlank() || black.isBlank() || moves.isBlank()) {
                _error.emit("Todos os campos são obrigatórios")
                return@launch
            }
            
            val partida = Partida(
                whitePlayer = white,
                blackPlayer = black,
                moves = moves,
                result = result
            )
            
            val success = repository.createPartida(partida)
            if (success) {
                _saveSuccess.emit(true)
            } else {
                _error.emit("Erro ao salvar partida")
            }
        }
    }
}
