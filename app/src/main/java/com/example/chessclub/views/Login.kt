package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclub.models.User
import com.example.chessclub.ui.theme.Alligator
import com.example.chessclub.ui.theme.Cinza
import com.example.chessclub.ui.theme.CorTextField
import com.example.chessclub.ui.theme.Salt
import com.example.chessclub.ui.theme.Swamp
import com.example.chessclub.ui.theme.Typography
import com.example.chessclub.viewmodel.LoginViewModel

@Composable
fun Login(
    viewModel: LoginViewModel,
    onEnterClicked: (User) -> Unit,
    onCadastroClicked: () -> Unit
) {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") } // cria estado para armazenar a mensagem de erro atual

    LaunchedEffect(Unit) {
        viewModel.loginSuccess.collect { user ->
            if (user != null) onEnterClicked(user)
        }
    }

    LaunchedEffect(Unit) { // efeito para observar o fluxo de erros da ViewModel
        viewModel.error.collect { message -> // coleta cada nova mensagem de erro emitida
            errorMessage = message // atualiza o estado da mensagem de erro na UI
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Cinza,
                        Cinza,
                        Alligator,
                        Cinza,
                        Cinza,
                        Cinza,
                        Cinza,
                        Alligator,
                        Cinza,
                        Cinza,
                        Cinza
                    )
                )
            ),
        Arrangement.Center,
        Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Chess Club",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(bottom = 100.dp, top = 10.dp)
        )
        Text(
            text = "Login",
            fontSize = 22.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 230.dp)
        )

        if (errorMessage.isNotEmpty()) { // verifica se existe uma mensagem de erro para exibir
            Text( // exibe o texto de erro na tela
                text = errorMessage, // define o texto da mensagem
                color = androidx.compose.ui.graphics.Color.Red, // define a cor vermelha para o erro
                fontSize = 14.sp, // define o tamanho da fonte do erro
                modifier = Modifier.padding(bottom = 8.dp) // adiciona espaçamento inferior
            )
        }

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = "" // limpa o erro ao começar a digitar novamente
            },
            label = {
                Text(
                    "Nome de usuário",
                    color = if (errorMessage == "Nome de usuário não cadastrado") androidx.compose.ui.graphics.Color.Red else Salt, // destaca o label em vermelho se for erro de usuário
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "Nome de usuário não cadastrado", // ativa o estado de erro visual no campo de usuário
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f),
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = "" // limpa o erro ao começar a digitar novamente
            },
            label = {
                Text(
                    "Senha", 
                    color = if (errorMessage == "Senha incorreta") androidx.compose.ui.graphics.Color.Red else Salt, // destaca o label em vermelho se for erro de senha
                    fontFamily = FontFamily.Serif, 
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "Senha incorreta", // ativa o estado de erro visual no campo de senha
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )
        Button(
            onClick = {
                viewModel.login(username, password)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Alligator
            ),
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(
                text = "Entrar",
                fontSize = 20.sp,
                style = Typography.bodyLarge
            )
        }
        Text(
            text = "Não tem uma conta?",
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(top = 80.dp)
        )
        Button(
            onClick = { onCadastroClicked() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Swamp
            ),
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(
                text = "Cadastre-se",
                fontSize = 20.sp,
                style = Typography.bodyLarge
            )
        }

    }
}

@Preview
@Composable
private fun LoginPreview() {
    // Preview desativado para evitar erro de falta de ViewModel
}
