package com.example.chessclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chessclub.views.Cadastrar
import com.example.chessclub.views.EditarPerfil
import com.example.chessclub.views.Login
import com.example.chessclub.views.MenuPrincipal


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController, "main/{user}") {
                composable("main/{user}") { entry ->
                    entry.arguments?.getString("user")?.let { user ->
                        MenuPrincipal(
                            user = user,
                            onEditarPerfilClick = {
                                navController.navigate("EditarPerfil/$user/ ")
                            }
                        )
                    } ?: LaunchedEffect(null) {
                        navController.navigate("Login")
                    }
                }
                composable("Login") {
                    Login(
                        onEnterClicked = { user ->
                            navController.navigate("main/${user.username}")
                        },
                        onCadastroClicked = {
                            navController.navigate("Cadastro")
                        }
                    )
                }
                composable("Cadastro") {
                    Cadastrar(
                        onCadastrarClicked = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("EditarPerfil/{username}/{email}") { entry ->
                    val username = entry.arguments?.getString("username") ?: ""
                    val email = entry.arguments?.getString("email") ?: ""
                    EditarPerfil(
                        username = username,
                        email = email,
                        onSalvarClicked = { user ->
                            navController.navigate("main/${user.username}") {
                                popUpTo("main/{user}") { inclusive = true }
                            }
                        },
                        onVoltarClicked = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
