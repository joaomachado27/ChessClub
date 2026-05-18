package com.example.chessclub.database

import androidx.room.Database // importa a anotação Database do Room
import androidx.room.RoomDatabase // importa a classe base RoomDatabase
import com.example.chessclub.database.dao.UserDAO // importa o DAO de usuário
import com.example.chessclub.database.entities.UserEntity // importa a entidade de usuário

@Database(entities = [UserEntity::class], version = 1, exportSchema = false) // configura o banco com a entidade UserEntity e versão 1
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO // declara a função abstrata para acessar o DAO de usuário
}
