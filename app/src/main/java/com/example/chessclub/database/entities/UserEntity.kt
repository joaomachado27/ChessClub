package com.example.chessclub.database.entities

import androidx.room.Entity // importa a anotação Entity do Room
import androidx.room.PrimaryKey // importa a anotação PrimaryKey do Room

@Entity(tableName = "users") // define que esta classe é uma entidade do banco de dados chamada users
data class UserEntity(
    @PrimaryKey val username: String, // define o nome de usuário como chave primária única
    val password: String, // armazena a senha do usuário
    val email: String // armazena o email do usuário
)
