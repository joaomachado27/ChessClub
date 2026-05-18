package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclub.ui.theme.*
import com.example.chessclub.viewmodel.AdicionarPartidaViewModel

@Composable
fun AdicionarPartida(
    currentUser: String,
    viewModel: AdicionarPartidaViewModel,
    onVoltar: () -> Unit,
    onSucesso: () -> Unit
) {
    var whitePlayer by remember { mutableStateOf(currentUser) }
    var blackPlayer by remember { mutableStateOf("") }
    var moves by remember { mutableStateOf("") }
    var result by remember { mutableIntStateOf(1) } // 1=White, 2=Black, 3=Draw

    LaunchedEffect(Unit) {
        viewModel.saveSuccess.collect { success ->
            if (success) onSucesso()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(Cinza, Alligator, Cinza)))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Adicionar Partida",
            fontSize = 24.sp,
            style = Typography.bodyLarge,
            color = Salt,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = whitePlayer,
            onValueChange = { whitePlayer = it },
            label = { Text("Brancas") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CorTextField,
            textStyle = TextStyle(color = Salt)
        )

        OutlinedTextField(
            value = blackPlayer,
            onValueChange = { blackPlayer = it },
            label = { Text("Pretas") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CorTextField,
            textStyle = TextStyle(color = Salt)
        )

        OutlinedTextField(
            value = moves,
            onValueChange = { moves = it },
            label = { Text("Movimentos") },
            placeholder = { Text("Ex: e4, e5, Nf3...") },
            modifier = Modifier.fillMaxWidth().height(150.dp).padding(vertical = 8.dp),
            colors = CorTextField,
            textStyle = TextStyle(color = Salt)
        )

        Text("Resultado:", color = Salt, modifier = Modifier.padding(top = 16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = result == 1, onClick = { result = 1 })
            Text("Brancas", color = Salt)
            RadioButton(selected = result == 2, onClick = { result = 2 })
            Text("Pretas", color = Salt)
            RadioButton(selected = result == 3, onClick = { result = 3 })
            Text("Empate", color = Salt)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.salvarPartida(whitePlayer, blackPlayer, moves, result) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Alligator)
        ) {
            Text("Salvar Partida")
        }

        TextButton(onClick = onVoltar) {
            Text("Cancelar", color = Salt)
        }
    }
}
