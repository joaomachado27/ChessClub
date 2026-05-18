package com.example.chessclub.database.dao

import androidx.room.Dao // importa a anotação Dao do Room
import androidx.room.Insert // importa a anotação Insert do Room
import androidx.room.OnConflictStrategy // importa as estratégias de conflito do Room
import androidx.room.Query // importa a anotação Query do Room
import androidx.room.Update // importa a anotação Update do Room
import com.example.chessclub.database.entities.UserEntity // importa a entidade UserEntity
import kotlinx.coroutines.flow.Flow // importa o Flow para observar mudanças nos dados

@Dao // define que esta interface é um Data Access Object (DAO)
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT) // insere um novo usuário e aborta se já existir (conflito de chave primária)
    suspend fun insert(user: UserEntity) // função suspensa para inserir usuário de forma assíncrona

    @Query("SELECT * FROM users") // consulta SQL para selecionar todos os usuários da tabela users
    fun getAllUsers(): Flow<List<UserEntity>> // retorna um Flow com a lista de usuários para atualização em tempo real

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1") // consulta para buscar um usuário específico pelo nome
    suspend fun getUserByUsername(username: String): UserEntity? // retorna o usuário ou nulo de forma assíncrona

    @Update // anotação para atualizar os dados de um usuário existente
    suspend fun update(user: UserEntity) // atualiza o registro do usuário no banco de dados

    @Query("UPDATE users SET password = :newPassword WHERE username = :username") // consulta para atualizar apenas a senha
    suspend fun updatePassword(username: String, newPassword: String) // executa a atualização da senha de forma assíncrona
}
