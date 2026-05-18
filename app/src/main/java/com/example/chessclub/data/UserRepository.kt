package com.example.chessclub.data

import com.example.chessclub.database.dao.UserDAO // importa o DAO de usuário para interagir com o banco
import com.example.chessclub.database.entities.UserEntity // importa a entidade UserEntity do banco de dados
import com.example.chessclub.models.User // importa o modelo de domínio User
import kotlinx.coroutines.flow.Flow // importa Flow para retorno reativo de dados
import kotlinx.coroutines.flow.map // importa map para transformar os dados do banco para o domínio

interface UserRepository {
    suspend fun createUser(user: User): Boolean // declara função para criar um novo usuário e retorna se teve sucesso
    fun getUsers(): Flow<List<User>> // declara função para buscar todos os usuários cadastrados
    suspend fun getUserByUsername(username: String): User? // declara função para buscar um usuário pelo nome
    suspend fun updateUser(username: String, email: String, password: String): Boolean // declara função para atualizar nome e email
    suspend fun updatePassword(username: String, newPassword: String): Boolean // declara função para alterar a senha do usuário
}

class UserRepositoryImpl(private val userDAO: UserDAO) : UserRepository {

    override suspend fun createUser(user: User): Boolean { // implementa a criação de usuário
        return try { // inicia bloco try para capturar possíveis erros de inserção
            userDAO.insert(UserEntity(user.username, user.senha, user.email)) // insere o usuário convertido para entidade no banco
            true // retorna verdadeiro se a inserção ocorreu com sucesso
        } catch (e: Exception) { // captura exceção caso ocorra erro (ex: usuário já existe)
            false // retorna falso indicando falha na criação do usuário
        }
    }

    override fun getUsers(): Flow<List<User>> { // implementa a busca de todos os usuários
        return userDAO.getAllUsers().map { entities -> // mapeia o Flow de entidades vindo do DAO
            entities.map { entity -> // mapeia cada entidade individual da lista
                User(entity.username, entity.password, entity.email) // converte a entidade do banco para o modelo User
            }
        }
    }

    override suspend fun getUserByUsername(username: String): User? { // implementa busca por nome de usuário
        return userDAO.getUserByUsername(username)?.let { entity -> // busca no banco e se encontrar executa o mapeamento
            User(entity.username, entity.password, entity.email) // converte a entidade encontrada para o modelo de domínio User
        }
    }

    override suspend fun updateUser(username: String, email: String, password: String): Boolean { // implementa atualização de perfil
        return try { // inicia bloco try para operação de atualização
            userDAO.update(UserEntity(username, password, email)) // executa o update no banco usando a entidade
            true // retorna sucesso se atualizou corretamente
        } catch (e: Exception) { // captura erros durante a atualização
            false // retorna falso se houve falha na operação
        }
    }

    override suspend fun updatePassword(username: String, newPassword: String): Boolean { // implementa alteração de senha
        return try { // inicia bloco try para alteração de senha
            userDAO.updatePassword(username, newPassword) // chama o DAO para atualizar o campo de senha
            true // retorna verdadeiro confirmando a alteração
        } catch (e: Exception) { // captura erros caso a operação falhe
            false // retorna falso indicando que a senha não foi alterada
        }
    }
}
