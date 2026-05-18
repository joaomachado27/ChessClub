package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
    onEnterClicked: (User) -> Unit,
    onCadastroClicked: () -> Unit,
    viewModel: LoginViewModel? = null
){

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(
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
            ))
            .verticalScroll(scrollState),
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
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text("Nome de usuário", color = Salt, fontFamily = FontFamily.Serif, fontSize = 16.sp)
            },
            textStyle = TextStyle(Salt, 16.sp),
            maxLines = 1,
            colors = CorTextField,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(0.75f),
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Senha", color = Salt, fontFamily = FontFamily.Serif, fontSize = 16.sp)
            },
            textStyle = TextStyle(Salt, 16.sp),
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            colors = CorTextField,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(0.75f)
        )
        Button(
            onClick = {
                onEnterClicked(
                    User(username, password)
                )
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
private fun LoginPreview(){
    Login(onEnterClicked = {}, onCadastroClicked ={})
}