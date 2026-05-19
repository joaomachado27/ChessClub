package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel // importa a classe base ViewModel do Android
import androidx.lifecycle.viewModelScope // importa o escopo de corrotinas vinculado à ViewModel
import com.example.chessclub.data.UserRepository // importa o repositório de usuários
import com.example.chessclub.models.User // importa o modelo de usuário
import kotlinx.coroutines.flow.MutableSharedFlow // importa SharedFlow para eventos únicos de navegação
import kotlinx.coroutines.flow.asSharedFlow // importa asSharedFlow para expor fluxo imutável
import kotlinx.coroutines.launch // importa o construtor de corrotina launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginSuccess = MutableSharedFlow<User?>() // cria um fluxo mutável para emitir sucesso no login
    val loginSuccess = _loginSuccess.asSharedFlow() // expõe o sucesso do login como fluxo somente leitura

    private val _error = MutableSharedFlow<String>() // cria um fluxo para emitir mensagens de erro
    val error = _error.asSharedFlow() // expõe as mensagens de erro como fluxo somente leitura

    fun login(username: String, password: String) { // função para processar a tentativa de login
        viewModelScope.launch { // inicia uma corrotina no escopo da ViewModel
            val user = repository.getUserByUsername(username) // busca o usuário no banco pelo nome fornecido
            if (user == null) { // verifica se o usuário não foi encontrado no banco
                _error.emit("Nome de usuário não cadastrado") // emite erro informando que o nome não existe
            } else if (user.senha != password) { // verifica se a senha digitada é diferente da senha do banco
                _error.emit("Senha incorreta") // emite erro informando que a senha está errada
            } else { // caso o usuário exista e a senha esteja correta
                _loginSuccess.emit(user) // emite o objeto usuário no fluxo de sucesso para navegação
            }
        }
    }
}
