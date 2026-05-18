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

    LaunchedEffect(Unit) {
        viewModel.updateSuccess.collect { user ->
            onAlterarClicked(user)
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

        OutlinedTextField(
            value = senhaAtual,
            onValueChange = { senhaAtual = it },
            label = {
                Text(
                    "Senha Atual",
                    color = Salt,
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )

        OutlinedTextField(
            value = novaSenha,
            onValueChange = { novaSenha = it },
            label = {
                Text(
                    "Nova Senha",
                    color = Salt,
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )

        OutlinedTextField(
            value = confirmarNovaSenha,
            onValueChange = { confirmarNovaSenha = it },
            label = {
                Text(
                    "Confirmar Nova Senha",
                    color = Salt,
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (novaSenha == confirmarNovaSenha && novaSenha.isNotEmpty()) {
                    viewModel.alterarSenha(username, senhaAtual, novaSenha)
                }
            },
            modifier = Modifier.fillMaxWidth(0.75f),
            enabled = senhaAtual.isNotEmpty() && novaSenha.isNotEmpty() && novaSenha == confirmarNovaSenha,
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
