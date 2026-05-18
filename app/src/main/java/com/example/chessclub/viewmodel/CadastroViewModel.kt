package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel // importa a classe base ViewModel do Android
import androidx.lifecycle.viewModelScope // importa o escopo de corrotinas da ViewModel
import com.example.chessclub.data.UserRepository // importa o repositório para acesso aos dados
import com.example.chessclub.models.User // importa o modelo de usuário
import kotlinx.coroutines.flow.MutableSharedFlow // importa SharedFlow para emitir eventos de uma única vez
import kotlinx.coroutines.flow.asSharedFlow // importa asSharedFlow para encapsulamento
import kotlinx.coroutines.launch // importa launch para iniciar corrotinas

class CadastroViewModel(private val repository: UserRepository) : ViewModel() {

    private val _cadastroSuccess = MutableSharedFlow<Boolean>() // cria fluxo privado para controle do sucesso no cadastro
    val cadastroSuccess = _cadastroSuccess.asSharedFlow() // expõe o sucesso do cadastro de forma imutável

    private val _error = MutableSharedFlow<String>() // cria fluxo privado para emitir erros durante o processo
    val error = _error.asSharedFlow() // expõe as mensagens de erro de forma imutável

    fun cadastrar(user: User) { // função para realizar o cadastro de um novo usuário
        viewModelScope.launch { // abre uma corrotina para operações assíncronas no banco
            val existingUser = repository.getUserByUsername(user.username) // verifica se já existe usuário com esse nome
            if (existingUser != null) { // se o usuário já estiver cadastrado
                _error.emit("Usuário já cadastrado") // emite erro informando a duplicidade
                return@launch // interrompe a execução da corrotina
            }
            
            val success = repository.createUser(user) // tenta criar o usuário no banco via repositório
            if (success) { // se a criação for bem sucedida
                _cadastroSuccess.emit(true) // emite sucesso no fluxo de cadastro
            } else { // se ocorrer algum erro inesperado no banco
                _error.emit("Erro ao realizar cadastro") // emite mensagem genérica de erro
            }
        }
    }
}
