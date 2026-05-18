package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclub.models.Partida
import com.example.chessclub.models.User
import com.example.chessclub.ui.theme.Alligator
import com.example.chessclub.ui.theme.Cinza
import com.example.chessclub.ui.theme.Typography
import com.example.chessclub.viewmodel.MenuPrincipalViewModel

@Composable
fun MenuPrincipal(
    user: String,
    viewModel: MenuPrincipalViewModel,
    onEditarPerfilClick: () -> Unit = {},
    onSobreNosClick: () -> Unit = {},
    onRankingClick: () -> Unit = {},
    onAddPartidaClick: () -> Unit = {}
) {

    val users by viewModel.users.collectAsState()
    val rankingWindow = viewModel.getRankingWindow(user, users)
    val partidasFlow = remember(user) { viewModel.getRecentPartidas(user) }
    val partidas by partidasFlow.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
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
                    .padding(horizontal = 24.dp),
                Arrangement.Top,
            ) {
                Classificacao(rankingWindow, onRankingClick)
                Spacer(Modifier.height(24.dp))
                UltimasPartidas(partidas.take(5))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                Arrangement.Center
            ) {
                Text(
                    text = "Sobre nós",
                    fontSize = 20.sp,
                    style = Typography.bodyLarge,
                    modifier = Modifier.clickable { onSobreNosClick() }
                )
            }
            Spacer(Modifier.height(100.dp)) // Espaço extra para o botão fixo não cobrir o conteúdo final
        }

        // Footer travado e elevado
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding() // Garante espaço acima da barra de navegação do sistema
                .background(Cinza.copy(alpha = 0.95f)) // Fundo para destacar o botão
                .padding(16.dp)
        ) {
            Button(
                onClick = onAddPartidaClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Alligator),
                shape = ShapeDefaults.Medium
            ) {
                Text("+ Adicionar Partida", fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun Classificacao(
    ranking: List<Pair<Int, User>>,
    onRankingClick: () -> Unit = {}
) {
    Text(
        text = "Classificação",
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        style = Typography.bodyLarge,
        modifier = Modifier
            .padding(bottom = 4.dp)
            .clickable { onRankingClick() }
    )
    Column {
        ranking.forEach { item ->
            Card(n = item.first.toString(), i = item.first - 1, user = item.second)
        }
    }
}

@Composable
private fun Card(n: String, i: Int, user: User) {
    val name = user.username
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$n.",
                fontSize = 18.sp,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    style = Typography.bodyLarge
                )
                Row {
                    Text(
                        text = "V: ${user.vitorias}",
                        fontSize = 12.sp,
                        color = Color.Green,
                        style = Typography.bodySmall
                    )
                    Text(
                        text = " / E: ${user.empates}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        style = Typography.bodySmall
                    )
                    Text(
                        text = " / D: ${user.derrotas}",
                        fontSize = 12.sp,
                        color = Color.Red,
                        style = Typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun UltimasPartidas(partidas: List<Partida>) {
    Text(
        text = "Últimas Partidas",
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        style = Typography.bodyLarge,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    if (partidas.isEmpty()) {
        Text(
            text = "Nenhuma partida recente",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
            items(partidas) { partida ->
                CardPartida(partida)
            }
        }
    }
}

@Composable
private fun CardPartida(partida: Partida) {
    val resultText = when (partida.result) {
        1 -> "Vitória Brancas"
        2 -> "Vitória Pretas"
        3 -> "Empate"
        else -> "Desconhecido"
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(ShapeDefaults.ExtraSmall)
            .background(Color.DarkGray.copy(alpha = 0.5f))
            .border(1.dp, Alligator)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${partida.whitePlayer} vs ${partida.blackPlayer}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = partida.moves,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }
        Text(
            text = resultText,
            fontSize = 14.sp,
            color = Alligator,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun MenuPrincipalViewModel() {
    // Preview desativado
}
