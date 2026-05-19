package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel // importa a classe base ViewModel
import androidx.lifecycle.viewModelScope // importa o escopo de corrotinas da ViewModel
import com.example.chessclub.data.UserRepository // importa o repositório para acesso ao banco
import com.example.chessclub.models.User // importa o modelo User
import kotlinx.coroutines.flow.MutableSharedFlow // importa SharedFlow para eventos de estado único
import kotlinx.coroutines.flow.asSharedFlow // importa função para encapsulamento de fluxo
import kotlinx.coroutines.launch // importa launch para operações assíncronas

class AlterarSenhaViewModel(private val repository: UserRepository) : ViewModel() {

    private val _updateSuccess = MutableSharedFlow<User>() // fluxo para indicar que a senha foi alterada com sucesso
    val updateSuccess = _updateSuccess.asSharedFlow() // expõe o sucesso da atualização de forma imutável

    private val _error = MutableSharedFlow<String>() // fluxo para emitir mensagens de erro na validação
    val error = _error.asSharedFlow() // expõe os erros de forma imutável para a View

    fun alterarSenha(username: String, senhaAtual: String, novaSenha: String, confirmacao: String) { // função para processar a troca de senha
        viewModelScope.launch { // inicia execução assíncrona
            if (novaSenha != confirmacao) { // verifica se a nova senha e a confirmação são diferentes
                _error.emit("A senha nova não corresponde") // emite erro de divergência na nova senha
                return@launch // encerra a corrotina
            }

            val user = repository.getUserByUsername(username) // verifica se o usuário existe e recupera seus dados
            if (user != null && user.senha == senhaAtual) { // valida se a senha atual informada está correta no banco
                val success = repository.updatePassword(username, novaSenha) // chama o repositório para atualizar no banco
                if (success) { // se o banco foi atualizado corretamente
                    _updateSuccess.emit(User(username, novaSenha, user.email)) // emite sucesso com os novos dados do usuário
                } else { // caso falte conexão ou erro interno no banco de dados
                    _error.emit("Erro ao alterar senha no banco") // informa erro de persistência genérico
                }
            } else { // se a senha atual digitada estiver incorreta comparada ao banco
                _error.emit("Senha atual incorreta") // emite erro de autenticação da senha antiga
            }
        }
    }
}
