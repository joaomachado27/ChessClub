package com.example.chessclub.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclub.R
import com.example.chessclub.ui.theme.Alligator
import com.example.chessclub.ui.theme.Cinza
import com.example.chessclub.ui.theme.Typography
import com.example.chessclub.viewmodel.MenuPrincipalViewModel

@Composable
fun MenuPrincipal(
    user: String,
    viewModel: MenuPrincipalViewModel,
    onEditarPerfilClick: () -> Unit = {},
    onSobreNosClick: () -> Unit = {}
) {

    val users by viewModel.users.collectAsState()

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = user,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    style = Typography.bodyLarge,
                    maxLines = 1
                )
                Text(
                    text = "Editar perfil",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    style = Typography.bodyLarge,
                    modifier = Modifier.clickable { onEditarPerfilClick() }
                )
            }
            Text(
                text = "Chess Club",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge
            )
        }
        Text(
            text = "Bem Vindo $user!",
            fontSize = 28.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(all = 24.dp),
            Arrangement.Top,
        ) {
            Classificacao(users.map { it.username })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            Arrangement.Center,
            Alignment.CenterVertically
        ) {
            Text(
                text = "Sobre nós |",
                fontSize = 20.sp,
                style = Typography.bodyLarge,
                modifier = Modifier.clickable{ onSobreNosClick() }
            )
            Image(
                painter = painterResource(id = R.drawable.logo_uepa_branca_prancheta_1_1024x724),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }
    }
}

@Composable
private fun Classificacao(
    qtde: List<String> = List(25) { "${it + 1}" }
) {
    Text(
        text = "Classificação",
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        style = Typography.bodyLarge,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    LazyColumn {
        itemsIndexed(items = qtde) { index, name ->
            Card(n = (index + 1).toString(), i = index, name = name)
        }
    }
}

@Composable
private fun Card(n: String, i: Int, name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(ShapeDefaults.Small)
            .background(if (i % 2 == 0) Color.DarkGray else Cinza)
            .border(4.dp, Alligator)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "$n.",
                fontSize = 18.sp,
                style = Typography.bodyLarge
            )
            Text(
                text = "\t$name",
                fontSize = 18.sp,
                style = Typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun LoginPreview() {
    // Preview desativado
}
