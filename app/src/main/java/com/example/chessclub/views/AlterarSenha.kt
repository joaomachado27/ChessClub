package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
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
import com.example.chessclub.viewmodel.AlterarSenhaViewModel

@Composable
fun AlterarSenha(
    username: String,
    viewModel: AlterarSenhaViewModel,
    onAlterarClicked: (User) -> Unit,
    onVoltarClicked: () -> Unit
) {
    var senhaAtual by remember { mutableStateOf("") }
    var novaSenha by remember { mutableStateOf("") }
    var confirmarNovaSenha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // cria estado para a mensagem de erro atual da troca de senha

    LaunchedEffect(Unit) {
        viewModel.updateSuccess.collect { user ->
            onAlterarClicked(user)
        }
    }

    LaunchedEffect(Unit) { // efeito para escutar os erros da ViewModel de alteração de senha
        viewModel.error.collect { message -> // coleta cada nova mensagem emitida
            errorMessage = message // atualiza o estado de erro visual na tela
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
        Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(192.dp))

        Text(
            text = "Alterar Senha para $username",
            fontSize = 22.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) { // se houver erro exibe texto em destaque
            Text( // componente de texto de erro
                text = errorMessage, // define a mensagem de erro
                color = androidx.compose.ui.graphics.Color.Red, // cor vermelha para alerta
                fontSize = 14.sp, // tamanho reduzido da fonte
                modifier = Modifier.padding(bottom = 8.dp) // margem inferior
            )
        }

        OutlinedTextField(
            value = senhaAtual,
            onValueChange = { 
                senhaAtual = it 
                errorMessage = "" // reseta erro ao começar a corrigir
            },
            label = {
                Text(
                    "Senha Actual",
                    color = if (errorMessage == "Senha atual incorreta") androidx.compose.ui.graphics.Color.Red else Salt, // destaca label se senha atual estiver errada
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "Senha atual incorreta", // habilita o visual de erro no campo de senha atual
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )

        OutlinedTextField(
            value = novaSenha,
            onValueChange = { 
                novaSenha = it 
                errorMessage = "" // reseta erro ao começar a corrigir
            },
            label = {
                Text(
                    "Nova Senha",
                    color = if (errorMessage == "A senha nova não corresponde") androidx.compose.ui.graphics.Color.Red else Salt, // destaca label se senhas novas não baterem
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "A senha nova não corresponde", // habilita erro visual no campo de nova senha
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )

        OutlinedTextField(
            value = confirmarNovaSenha,
            onValueChange = { 
                confirmarNovaSenha = it 
                errorMessage = "" // reseta erro ao começar a corrigir
            },
            label = {
                Text(
                    "Confirmar Nova Senha",
                    color = if (errorMessage == "A senha nova não corresponde") androidx.compose.ui.graphics.Color.Red else Salt, // destaca label se senhas novas não baterem
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            isError = errorMessage == "A senha nova não corresponde", // habilita erro visual no campo de confirmação
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.alterarSenha(username, senhaAtual, novaSenha, confirmarNovaSenha) // chama a troca de senha passando a confirmação para a ViewModel
            },
            modifier = Modifier.fillMaxWidth(0.75f),
            enabled = senhaAtual.isNotEmpty() && novaSenha.isNotEmpty() && confirmarNovaSenha.isNotEmpty(), // valida campos preenchidos
            colors = ButtonDefaults.buttonColors(
                containerColor = Alligator
            )
        ) {
            Text(
                text = "Confirmar Alteração",
                fontSize = 20.sp,
                style = Typography.bodyLarge
            )
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = { onVoltarClicked() },
            modifier = Modifier.fillMaxWidth(0.75f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Swamp
            )
        ) {
            Text(
                text = "Voltar",
                fontSize = 20.sp,
                style = Typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun AlterarSenhaPreview() {
    // Preview desativado
}
