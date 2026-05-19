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

    fun cadastrar(user: User, confirmacao: String) { // função para realizar o cadastro validando os dados
        viewModelScope.launch { // abre uma corrotina para operações assíncronas no banco
            if (user.senha != confirmacao) { // verifica se a senha e a confirmação são diferentes
                _error.emit("As senhas não correspondem") // emite erro de divergência de senhas
                return@launch // encerra a corrotina
            }

            val existingUser = repository.getUserByUsername(user.username) // verifica se já existe usuário com esse nome
            if (existingUser != null) { // se o usuário já estiver cadastrado no banco
                _error.emit("Nome de usuário já cadastrado") // emite erro informando a duplicidade de nome
                return@launch // interrompe a execução da corrotina
            }
            
            val success = repository.createUser(user) // tenta criar o usuário no banco via repositório
            if (success) { // se a criação for bem sucedida na persistência
                _cadastroSuccess.emit(true) // emite sucesso no fluxo de cadastro para a View
            } else { // se ocorrer algum erro inesperado durante a escrita no banco
                _error.emit("Erro ao realizar cadastro") // emite mensagem genérica de falha
            }
        }
    }
}
