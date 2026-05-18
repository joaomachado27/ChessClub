package com.example.chessclub

import android.app.Application // importa a classe base Application do Android
import com.example.chessclub.di.databaseModule // importa o módulo de banco de dados do Koin
import com.example.chessclub.di.repositoryModule // importa o módulo de repositório do Koin
import com.example.chessclub.di.viewModelModule // importa o módulo de viewmodels do Koin
import org.koin.android.ext.koin.androidContext // importa extensão para prover contexto Android ao Koin
import org.koin.core.context.startKoin // importa função para iniciar o Koin

class ChessClubApplication : Application() { // define a classe customizada da aplicação

    override fun onCreate() { // chamado quando a aplicação é iniciada
        super.onCreate() // chama a implementação base do Android
        
        startKoin { // inicializa o framework Koin
            androidContext(this@ChessClubApplication) // passa o contexto da aplicação para o Koin
            modules(listOf(databaseModule, repositoryModule, viewModelModule)) // carrega a lista de módulos definidos
        }
    }
}
