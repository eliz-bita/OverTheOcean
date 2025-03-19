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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog

@Composable
fun ExtratoDePontosScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Extrato",
            fontSize = 24.sp,
            color = Color(0xFF2E4374),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

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
    val dados = listOf(
        Triple("Entrada", "+50", Icons.Filled.Info),
        Triple("Saída", "-100", Icons.Filled.Info),
        Triple("Entrada", "+1000", Icons.Filled.Info),
        Triple("Entrada", "+50", Icons.Filled.Info)
    )

    var mostrarPop by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
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
                fontSize = 20.sp,
                color = Color(0xFF002140),
                textAlign = TextAlign.Center
            )
            Text(
                text = "PONTOS",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF002140),
                textAlign = TextAlign.Center
            )
            Text(
                text = "INFO",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF002140),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn {
            items(dados) { (tipo, pontos, info)->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = tipo,
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = pontos,
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                mostrarPop = true
                            }
                        ) {
                            Icon(
                                imageVector = info,
                                contentDescription = "Ícone de informação",
                                tint = Color(0xFFC8D5F5),
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = Color(0xFFD3D3D3)
                )
                PopupInfo(mostrarPop) { mostrarPop = false }
            }
        }
    }
}

@Composable
fun PopupInfo(mostrarPop: Boolean, onDismiss: () -> Unit) {
    if (mostrarPop){
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
                    .clip(shape = RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ){
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = "Data: 21/10/2024")
                    Text(text = "Hora: 11:43")
                    IconButton(
                        onClick = { onDismiss() }
                    ) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = "Fechar")
                    }
                }
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