package com.example.chessclub.di

import androidx.room.Room // importa o construtor do banco Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.chessclub.data.PartidaRepository
import com.example.chessclub.data.PartidaRepositoryImpl
import com.example.chessclub.data.UserRepository // importa interface do repositório
import com.example.chessclub.data.UserRepositoryImpl // importa implementação do repositório
import com.example.chessclub.database.UserDatabase // importa definição do banco de dados
import com.example.chessclub.database.entities.PartidaEntity
import com.example.chessclub.database.entities.UserEntity
import com.example.chessclub.viewmodel.AdicionarPartidaViewModel
import com.example.chessclub.viewmodel.AlterarSenhaViewModel // importa ViewModel de alteração de senha
import com.example.chessclub.viewmodel.CadastroViewModel // importa ViewModel de cadastro
import com.example.chessclub.viewmodel.EditarPerfilViewModel // importa ViewModel de edição de perfil
import com.example.chessclub.viewmodel.LoginViewModel // importa ViewModel de login
import com.example.chessclub.viewmodel.MenuPrincipalViewModel // importa ViewModel do menu principal
import com.example.chessclub.viewmodel.RankingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random
import org.koin.androidx.viewmodel.dsl.viewModel // importa DSL do Koin para ViewModels
import org.koin.dsl.module // importa DSL do Koin para módulos

val databaseModule = module { // define o módulo de injeção para o banco de dados
    single { // define uma instância única (singleton) do banco
        Room.databaseBuilder( // constrói o banco de dados
            get(), // recupera o contexto do Android provido pelo Koin
            UserDatabase::class.java, // define a classe do banco
            "chess_club_db" // nome do arquivo do banco de dados
        )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                seedDatabase()
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                seedDatabase()
            }

            private fun seedDatabase() {
                CoroutineScope(Dispatchers.IO).launch {
                    val database = get<UserDatabase>()
                    val userDAO = database.userDAO()
                    val partidaDAO = database.partidaDAO()

                    // Função auxiliar para gerar UserEntity com stats random
                    fun createLegend(name: String, v: Int? = null, e: Int? = null, d: Int? = null): UserEntity {
                        return UserEntity(
                            username = name,
                            password = "123",
                            email = "${name.lowercase().replace(" ", "")}@chess.com",
                            v = v ?: Random.nextInt(0, 101),
                            e = e ?: Random.nextInt(0, 101),
                            d = d ?: Random.nextInt(0, 101)
                        )
                    }

                    val initialUsers = listOf(
                        createLegend("Bobby Fischer"),
                        createLegend("Magnus Carlsen"),
                        createLegend("Hikaru Nakamura"),
                        createLegend("Cabiano Faruana"),
                        createLegend("Levy Rozman"),
                        createLegend("Kasparov"),
                        createLegend("Capablanca"),
                        createLegend("Mikhail Tal"),
                        createLegend("Kramnik", v = 0, e = 0, d = 10000)
                    )

                    initialUsers.forEach { userDAO.insert(it) }

                    // Adicionando uma partida inicial simbólica
                    partidaDAO.insertOnly(PartidaEntity(
                        whitePlayer = "Kasparov", 
                        blackPlayer = "Mikhail Tal", 
                        moves = "1. e4 e5 2. Nf3 Nc6", 
                        result = 1
                    ))
                }
            }
        })
        .fallbackToDestructiveMigration()
        .build() // finaliza a construção do objeto banco
    }
    
    single { // define uma instância única para o DAO
        get<UserDatabase>().userDAO() // obtém o DAO a partir da instância do banco injetada
    }

    single {
        get<UserDatabase>().partidaDAO()
    }
}

val repositoryModule = module { // define o módulo de injeção para os repositórios
    single<UserRepository> { // define que UserRepository será provido via UserRepositoryImpl
        UserRepositoryImpl(get()) // injeta o DAO necessário para a construção do repositório
    }

    single<PartidaRepository> {
        PartidaRepositoryImpl(get())
    }
}

val viewModelModule = module { // define o módulo de injeção para as ViewModels
    viewModel { LoginViewModel(get()) } // provê a LoginViewModel injetando o repositório
    viewModel { CadastroViewModel(get()) } // provê a CadastroViewModel injetando o repositório
    viewModel { MenuPrincipalViewModel(get(), get()) } // provê a MenuPrincipalViewModel injetando o repositório
    viewModel { EditarPerfilViewModel(get()) } // provê a EditarPerfilViewModel injetando o repositório
    viewModel { AlterarSenhaViewModel(get()) } // provê a AlterarSenhaViewModel injetando o repositório
    viewModel { RankingViewModel(get()) }
    viewModel { AdicionarPartidaViewModel(get()) }
}
