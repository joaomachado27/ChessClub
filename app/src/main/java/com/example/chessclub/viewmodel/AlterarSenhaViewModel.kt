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

    fun alterarSenha(username: String, senhaAtual: String, novaSenha: String) { // função para processar a troca de senha
        viewModelScope.launch { // inicia execução assíncrona
            val user = repository.getUserByUsername(username) // verifica se o usuário existe e recupera seus dados
            if (user != null && user.senha == senhaAtual) { // valida se a senha atual informada está correta
                val success = repository.updatePassword(username, novaSenha) // chama o repositório para atualizar no banco
                if (success) { // se o banco foi atualizado corretamente
                    _updateSuccess.emit(User(username, novaSenha, user.email)) // emite sucesso com os dados atualizados
                } else { // caso falte conexão ou erro interno no banco
                    _error.emit("Erro ao alterar senha no banco") // informa erro de persistência
                }
            } else { // se a senha atual digitada estiver errada
                _error.emit("Senha atual incorreta") // emite erro de autenticação da senha antiga
            }
        }
    }
}
