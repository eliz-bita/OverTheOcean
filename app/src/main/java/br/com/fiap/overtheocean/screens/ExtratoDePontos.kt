package br.com.fiap.overtheocean.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ExtratoDePontosScreen() {
    // Coluna principal com padding ajustado para aproximar título e tabela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título com fonte Inter
        Text(
            text = "Extrato",
            fontSize = 50.sp,
            color = Color(0xFF2d347b),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Espaço reduzido entre o título e a tabela
        Spacer(modifier = Modifier.height(30.dp))

        // Tabela posicionada mais acima na tela
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            TabelaExtratoPontos()
        }
    }
}

@Composable
fun TabelaExtratoPontos() {
    // Dados atualizados com os sinais + e - e linhas adicionais
    val dados = listOf(
        Triple("Entrada", "+50", Icons.Filled.Info),
        Triple("Saída", "-100", Icons.Filled.Info),
        Triple("Entrada", "+1000", Icons.Filled.Info),
        Triple("Entrada", "+50", Icons.Filled.Info),
        Triple("Entrada", "+100", Icons.Filled.Info)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Cabeçalho da tabela com sombra mais pronunciada
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, shape = RoundedCornerShape(0.dp))
                .background(Color(0xFFE0E7F9))
                .padding(vertical = 18.dp)
        ) {
            Text(
                text = "TIPO",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF002140),
                textAlign = TextAlign.Center
            )
            Text(
                text = "PONTOS",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF002140),
                textAlign = TextAlign.Center
            )
            Text(
                text = "INFO",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF002140),
                textAlign = TextAlign.Center
            )
        }

        // Conteúdo da tabela com linhas maiores
        LazyColumn {
            items(dados) { (tipo, pontos, info) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = tipo,
                        modifier = Modifier.weight(1f),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = pontos,
                        modifier = Modifier.weight(1f),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = info,
                            contentDescription = "Ícone de informação",
                            tint = Color(0xFFC8D5F5),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                // Linha divisória mais visível entre as linhas
                Divider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = Color(0xFFD3D3D3)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewExtratoScreen() {
    Surface(color = Color.White) {
        ExtratoDePontosScreen()
    }
}