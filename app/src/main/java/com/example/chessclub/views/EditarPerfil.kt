package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chessclub.models.User
import com.example.chessclub.ui.theme.Alligator
import com.example.chessclub.ui.theme.Cinza
import com.example.chessclub.ui.theme.CorTextField
import com.example.chessclub.ui.theme.Salt
import com.example.chessclub.ui.theme.Swamp
import com.example.chessclub.ui.theme.Typography
import com.example.chessclub.viewmodel.EditarPerfilViewModel


@Composable
fun EditarPerfil(
    username: String,
    email: String,
    viewModel: EditarPerfilViewModel,
    onSalvarClicked: (User) -> Unit,
    onVoltarClicked: () -> Unit,
    onAlterarClicked: () -> Unit
) {

    var usernameEdited by remember { mutableStateOf(username) }
    var emailEdited by remember { mutableStateOf(email) }
    var password by remember { mutableStateOf("") }
    var isDisplayDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.updateSuccess.collect { user ->
            if (user != null) onSalvarClicked(user)
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
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Editar Perfil",
                fontSize = 22.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp, end = 170.dp)
            )

            OutlinedTextField(
                value = usernameEdited,
                onValueChange = {
                    usernameEdited = it
                },
                label = {
                    Text(
                        "Nome de usuário",
                        color = Salt,
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    )
                },
                textStyle = TextStyle(Salt, 16.sp),
                maxLines = 1,
                colors = CorTextField,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.75f)
            )

            OutlinedTextField(
                value = emailEdited,
                onValueChange = {
                    emailEdited = it
                },
                label = {
                    Text("E-mail", color = Salt, fontFamily = FontFamily.Serif, fontSize = 16.sp)
                },
                textStyle = TextStyle(Salt, 16.sp),
                maxLines = 1,
                colors = CorTextField,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.75f),
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Alterar Senha",
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge,
                modifier = Modifier.clickable{ onAlterarClicked() }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        isDisplayDialog = true
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Alligator
                    )
                ) {
                    Text(
                        text = "Salvar Alterações",
                        fontSize = 20.sp,
                        style = Typography.bodyLarge
                    )
                }

                if(isDisplayDialog){
                    DialogSenha(
                        password = password,
                        onPasswordChange = { password = it },
                        onConfirm = {
                            viewModel.salvarAlteracoes(usernameEdited, emailEdited, password)
                            isDisplayDialog = false
                        },
                        onDismiss = { isDisplayDialog = false }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { onVoltarClicked() },
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
    }
}

@Composable
private fun DialogSenha(
    password: String,
    onPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
                .clip(RoundedCornerShape(8))
                .background(Cinza)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                text = "Confirmar Alteração",
                fontSize = 22.sp,
                style = Typography.bodyLarge
            )
            Spacer(Modifier.size(16.dp))
            Text(
                text = "Digite sua senha",
                fontSize = 22.sp,
                style = Typography.bodyLarge
            )
            Spacer(Modifier.size(24.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    onPasswordChange(it)
                },
                label = {
                    Text("Senha", color = Salt, fontFamily = FontFamily.Serif, fontSize = 16.sp)
                },
                textStyle = TextStyle(Salt, 16.sp),
                visualTransformation = PasswordVisualTransformation(),
                maxLines = 1,
                colors = CorTextField,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onConfirm() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Alligator
                    )
                ) {
                    Text(
                        text = "Confirmar",
                        fontSize = 20.sp,
                        style = Typography.bodyLarge
                    )
                }
                Button(
                    onClick = { onDismiss() },
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
    }
}

@Preview
@Composable
private fun EditarPerfilPreview() {
    // Preview desativado
}
