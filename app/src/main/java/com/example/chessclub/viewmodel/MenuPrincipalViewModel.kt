package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel // importa a classe base ViewModel
import androidx.lifecycle.viewModelScope // importa o escopo de corrotina da ViewModel
import com.example.chessclub.data.UserRepository // importa o repositório de usuários
import com.example.chessclub.models.User // importa o modelo de usuário
import kotlinx.coroutines.flow.MutableStateFlow // importa MutableStateFlow para estado observável reativo
import kotlinx.coroutines.flow.StateFlow // importa StateFlow imutável para a View
import kotlinx.coroutines.flow.asStateFlow // importa função de conversão para imutabilidade
import kotlinx.coroutines.launch // importa launch para disparar corrotinas

class MenuPrincipalViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList()) // cria estado privado para armazenar lista de usuários
    val users: StateFlow<List<User>> = _users.asStateFlow() // expõe a lista de usuários como fluxo de estado imutável

    init { // bloco de inicialização executado ao criar a ViewModel
        fetchUsers() // dispara a busca inicial de usuários no banco
    }

    private fun fetchUsers() { // função para buscar usuários e observar mudanças
        viewModelScope.launch { // inicia corrotina vinculada ao ciclo de vida da ViewModel
            repository.getUsers().collect { userList -> // coleta as atualizações do Flow de usuários do repositório
                _users.value = userList // atualiza o valor do estado com a nova lista recebida
            }
        }
    }
}
