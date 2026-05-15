package com.example.chessclub.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclub.ui.theme.Alligator
import com.example.chessclub.ui.theme.Cinza
import com.example.chessclub.ui.theme.Swamp
import com.example.chessclub.ui.theme.Typography

@Composable
fun SobreNos(
    onVoltarClicked: () -> Unit
)
{
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
        Alignment.Start
    ){
        Column(
            Modifier
                .padding(32.dp)
        ) {
            Text(
                text = "Equipe",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "João Vitor Cardoso Machado",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Gabriel Castro Rodrigues",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Maurício Câncio da Cunha",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyLarge
            )
            Spacer(Modifier.height(48.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
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

@Preview
@Composable
private fun SobreNosPreview() {
    SobreNos(
        onVoltarClicked = {}
    )
}