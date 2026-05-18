package com.example.chessclub.di

import androidx.room.Room // importa o construtor do banco Room
import com.example.chessclub.data.UserRepository // importa interface do repositório
import com.example.chessclub.data.UserRepositoryImpl // importa implementação do repositório
import com.example.chessclub.database.UserDatabase // importa definição do banco de dados
import com.example.chessclub.viewmodel.AlterarSenhaViewModel // importa ViewModel de alteração de senha
import com.example.chessclub.viewmodel.CadastroViewModel // importa ViewModel de cadastro
import com.example.chessclub.viewmodel.EditarPerfilViewModel // importa ViewModel de edição de perfil
import com.example.chessclub.viewmodel.LoginViewModel // importa ViewModel de login
import com.example.chessclub.viewmodel.MenuPrincipalViewModel // importa ViewModel do menu principal
import org.koin.androidx.viewmodel.dsl.viewModel // importa DSL do Koin para ViewModels
import org.koin.dsl.module // importa DSL do Koin para módulos

val databaseModule = module { // define o módulo de injeção para o banco de dados
    single { // define uma instância única (singleton) do banco
        Room.databaseBuilder( // constrói o banco de dados
            get(), // recupera o contexto do Android provido pelo Koin
            UserDatabase::class.java, // define a classe do banco
            "chess_club_db" // nome do arquivo do banco de dados
        ).build() // finaliza a construção do objeto banco
    }
    
    single { // define uma instância única para o DAO
        get<UserDatabase>().userDAO() // obtém o DAO a partir da instância do banco injetada
    }
}

val repositoryModule = module { // define o módulo de injeção para os repositórios
    single<UserRepository> { // define que UserRepository será provido via UserRepositoryImpl
        UserRepositoryImpl(get()) // injeta o DAO necessário para a construção do repositório
    }
}

val viewModelModule = module { // define o módulo de injeção para as ViewModels
    viewModel { LoginViewModel(get()) } // provê a LoginViewModel injetando o repositório
    viewModel { CadastroViewModel(get()) } // provê a CadastroViewModel injetando o repositório
    viewModel { MenuPrincipalViewModel(get()) } // provê a MenuPrincipalViewModel injetando o repositório
    viewModel { EditarPerfilViewModel(get()) } // provê a EditarPerfilViewModel injetando o repositório
    viewModel { AlterarSenhaViewModel(get()) } // provê a AlterarSenhaViewModel injetando o repositório
}
