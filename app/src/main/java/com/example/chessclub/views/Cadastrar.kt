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
import com.example.chessclub.viewmodel.CadastroViewModel

@Composable
fun Cadastrar(
    viewModel: CadastroViewModel,
    onCadastrarClicked: (User) -> Unit
) {

    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") } // cria estado para armazenar a mensagem de erro atual

    LaunchedEffect(Unit) {
        viewModel.cadastroSuccess.collect { success ->
            if (success) onCadastrarClicked(User(username, password, email))
        }
    }

    LaunchedEffect(Unit) { // observa o fluxo de erros da ViewModel de cadastro
        viewModel.error.collect { message -> // coleta as mensagens de erro emitidas
            errorMessage = message // atualiza o estado da mensagem de erro na interface
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
            modifier = Modifier.padding(bottom = 100.dp, top = 0.dp)
        )
        Text(
            text = "Cadastro",
            fontSize = 22.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, end = 200.dp)
        )

        if (errorMessage.isNotEmpty()) { // exibe a mensagem de erro se houver uma ativa
            Text( // componente de texto para o erro
                text = errorMessage, // define a mensagem a ser exibida
                color = androidx.compose.ui.graphics.Color.Red, // define a cor vermelha para alerta de erro
                fontSize = 14.sp, // define o tamanho da fonte do erro
                modifier = Modifier.padding(bottom = 8.dp) // adiciona margem inferior
            )
        }

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = "" // limpa o erro ao digitar
            },
            label = {
                Text(
                    "Nome de usuário",
                    color = if (errorMessage == "Nome de usuário já cadastrado") androidx.compose.ui.graphics.Color.Red else Salt, // destaca label em vermelho se erro for duplicidade
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "Nome de usuário já cadastrado", // ativa estado visual de erro no campo de usuário
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f),
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = "" // limpa o erro ao digitar
            },
            label = {
                Text("Email", color = Salt, fontFamily = FontFamily.Serif, fontSize = 16.sp)
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f),
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = "" // limpa o erro ao digitar
            },
            label = {
                Text(
                    "Senha", 
                    color = if (errorMessage == "As senhas não correspondem") androidx.compose.ui.graphics.Color.Red else Salt, // destaca label em vermelho se senhas não baterem
                    fontFamily = FontFamily.Serif, 
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "As senhas não correspondem", // ativa estado de erro visual no campo de senha
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = "" // limpa o erro ao digitar
            },
            label = {
                Text(
                    "Confirmar senha",
                    color = if (errorMessage == "As senhas não correspondem") androidx.compose.ui.graphics.Color.Red else Salt, // destaca label em vermelho se senhas não baterem
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "As senhas não correspondem", // ativa estado de erro visual no campo de confirmação
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f),
        )
        Button(
            onClick = {
                viewModel.cadastrar(User(username, password, email), confirmPassword) // chama o cadastro passando a confirmação para validar
            },
            enabled = username.isNotEmpty() &&
                      email.isNotEmpty() &&
                      password.isNotEmpty() &&
                      confirmPassword.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Swamp
            ),
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(
                text = "Cadastrar",
                fontSize = 20.sp,
                style = Typography.bodyLarge
            )
        }


    }
}

@Preview
@Composable
private fun CadastrarPreview() {
    // Preview desativado
}
