package com.example.chessclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chessclub.viewmodel.AlterarSenhaViewModel
import com.example.chessclub.viewmodel.CadastroViewModel
import com.example.chessclub.viewmodel.EditarPerfilViewModel
import com.example.chessclub.viewmodel.LoginViewModel
import com.example.chessclub.viewmodel.MenuPrincipalViewModel
import com.example.chessclub.views.AlterarSenha
import com.example.chessclub.views.Cadastrar
import com.example.chessclub.views.EditarPerfil
import com.example.chessclub.views.Login
import com.example.chessclub.views.MenuPrincipal
import com.example.chessclub.views.SobreNos
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "Login") {
                composable("main/{user}") { entry ->
                    val user = entry.arguments?.getString("user") ?: ""
                    val viewModel: MenuPrincipalViewModel = koinViewModel()
                    MenuPrincipal(
                        user = user,
                        viewModel = viewModel,
                        onEditarPerfilClick = {
                            navController.navigate("EditarPerfil/$user/ ")
                        },
                        onSobreNosClick = {
                            navController.navigate("SobreNos")
                        }
                    )
                }
                composable("Login") {
                    val viewModel: LoginViewModel = koinViewModel()
                    Login(
                        viewModel = viewModel,
                        onEnterClicked = { user ->
                            navController.navigate("main/${user.username}")
                        },
                        onCadastroClicked = {
                            navController.navigate("Cadastro")
                        }
                    )
                }
                composable("Cadastro") {
                    val viewModel: CadastroViewModel = koinViewModel()
                    Cadastrar(
                        viewModel = viewModel,
                        onCadastrarClicked = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("EditarPerfil/{username}/{email}") { entry ->
                    val username = entry.arguments?.getString("username") ?: ""
                    val email = entry.arguments?.getString("email") ?: ""
                    val viewModel: EditarPerfilViewModel = koinViewModel()
                    EditarPerfil(
                        username = username,
                        email = email,
                        viewModel = viewModel,
                        onSalvarClicked = { user ->
                            navController.navigate("main/${user.username}") {
                                popUpTo("main/{user}") { inclusive = true }
                            }
                        },
                        onVoltarClicked = {
                            navController.popBackStack()
                        },
                        onAlterarClicked = {
                            navController.navigate("AlterarSenha/$username")
                        }
                    )
                }
                composable("AlterarSenha/{username}") { entry ->
                    val username = entry.arguments?.getString("username") ?: ""
                    val viewModel: AlterarSenhaViewModel = koinViewModel()
                    AlterarSenha(
                        username = username,
                        viewModel = viewModel,
                        onAlterarClicked = { user ->
                            navController.navigate("main/${user.username}") {
                                popUpTo("main/{user}") { inclusive = true }
                            }
                        },
                        onVoltarClicked = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("SobreNos") {
                    SobreNos(
                        onVoltarClicked = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
