package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel // importa a classe base ViewModel
import androidx.lifecycle.viewModelScope // importa o escopo para corrotinas
import com.example.chessclub.data.UserRepository // importa o repositório de dados
import com.example.chessclub.models.User // importa o modelo de usuário
import kotlinx.coroutines.flow.MutableSharedFlow // importa SharedFlow para eventos pontuais
import kotlinx.coroutines.flow.asSharedFlow // importa função para expor fluxo imutável
import kotlinx.coroutines.launch // importa launch para execução assíncrona

class EditarPerfilViewModel(private val repository: UserRepository) : ViewModel() {

    private val _updateSuccess = MutableSharedFlow<User?>() // cria fluxo para sinalizar sucesso na atualização
    val updateSuccess = _updateSuccess.asSharedFlow() // expõe o evento de sucesso de forma imutável

    private val _error = MutableSharedFlow<String>() // cria fluxo para sinalizar erros durante a edição
    val error = _error.asSharedFlow() // expõe as mensagens de erro de forma imutável

    fun salvarAlteracoes(username: String, email: String, passwordConfirm: String) { // função para salvar novos dados do perfil
        viewModelScope.launch { // inicia corrotina no escopo da ViewModel
            val user = repository.getUserByUsername(username) // busca o usuário atual no banco para validar senha
            if (user != null && user.senha == passwordConfirm) { // valida se o usuário existe e a senha está correta
                val updated = repository.updateUser(username, email, user.senha) // chama o repositório para atualizar no banco
                if (updated) { // se a atualização no banco foi bem sucedida
                    _updateSuccess.emit(User(username, user.senha, email)) // emite o usuário atualizado no fluxo de sucesso
                } else { // se ocorreu falha na escrita do banco
                    _error.emit("Erro ao atualizar perfil") // informa erro de atualização
                }
            } else { // caso a senha digitada no diálogo esteja incorreta
                _error.emit("Senha incorreta") // informa erro de validação de senha
            }
        }
    }
}
