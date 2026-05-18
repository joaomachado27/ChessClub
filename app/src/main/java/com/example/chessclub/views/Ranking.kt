package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclub.ui.theme.*
import com.example.chessclub.viewmodel.RankingViewModel

@Composable
fun Ranking(
    viewModel: RankingViewModel,
    onVoltarClicked: () -> Unit
) {
    val ranking by viewModel.ranking.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(Cinza, Alligator, Cinza)
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Classificação",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Salt,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            itemsIndexed(ranking) { index, user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${index + 1}. ${user.username}",
                            fontSize = 20.sp,
                            color = Salt,
                            style = Typography.bodyLarge
                        )
                        Row {
                            Text(
                                text = "V: ${user.vitorias}",
                                fontSize = 14.sp,
                                color = Color.Green,
                                style = Typography.bodySmall
                            )
                            Text(
                                text = " / E: ${user.empates}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                style = Typography.bodySmall
                            )
                            Text(
                                text = " / D: ${user.derrotas}",
                                fontSize = 14.sp,
                                color = Color.Red,
                                style = Typography.bodySmall
                            )
                        }
                    }
                }
                HorizontalDivider(color = Salt.copy(alpha = 0.3f))
            }
        }

        Button(
            onClick = onVoltarClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Swamp),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Voltar", fontSize = 18.sp)
        }
    }
}
