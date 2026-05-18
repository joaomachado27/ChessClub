package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel // importa a classe base ViewModel
import androidx.lifecycle.viewModelScope // importa o escopo de corrotina da ViewModel
import com.example.chessclub.data.UserRepository // importa o repositório de usuários
import com.example.chessclub.models.User // importa o modelo de usuário
import kotlinx.coroutines.flow.MutableStateFlow // importa MutableStateFlow para estado observável reativo
import kotlinx.coroutines.flow.StateFlow // importa StateFlow imutável para a View
import com.example.chessclub.data.PartidaRepository
import com.example.chessclub.models.Partida
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch // importa launch para disparar corrotinas

class MenuPrincipalViewModel(
    private val repository: UserRepository,
    private val partidaRepository: PartidaRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    fun getRecentPartidas(username: String): StateFlow<List<Partida>> {
        return partidaRepository.getPartidasByUser(username)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    init {
        fetchRanking()
    }

    private fun fetchRanking() {
        viewModelScope.launch {
            repository.getRanking().collect { userList ->
                _users.value = userList
            }
        }
    }
// ... restante da classe getRankingWindow

    fun getRankingWindow(currentUser: String, allUsers: List<User>): List<Pair<Int, User>> {
        if (allUsers.isEmpty()) return emptyList()

        val indexedUsers = allUsers.mapIndexed { index, user -> (index + 1) to user }
        val userIndex = allUsers.indexOfFirst { it.username == currentUser }

        if (userIndex == -1) {
            return indexedUsers.take(5)
        }

        var start = userIndex - 2
        var end = userIndex + 2

        if (start < 0) {
            end += (0 - start)
            start = 0
        }

        if (end >= allUsers.size) {
            start -= (end - (allUsers.size - 1))
            end = allUsers.size - 1
        }

        start = start.coerceAtLeast(0)

        return indexedUsers.subList(start, (end + 1).coerceAtMost(allUsers.size))
    }
}
