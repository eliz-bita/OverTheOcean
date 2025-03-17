package br.com.fiap.overtheocean.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.overtheocean.R
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

@Composable
fun PontosDeColetaScreen() {
    Scaffold { innerPadding ->
        // Primeiro um Spacer grande para empurrar tudo para baixo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espaçador para empurrar o conteúdo para baixo
            Spacer(modifier = Modifier.height(70.dp))

            // Título
            Text(
                text = "Pontos de Coleta",
                color = Color(0xFF002140),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Novo Card com Mapa
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val initialPosition = LatLng(-23.55052, -46.633308) // São Paulo como exemplo
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(initialPosition, 12f)
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = true)
                ) {
                    Marker(
                        state = MarkerState(position = initialPosition),
                        title = "Ponto de Coleta",
                        snippet = "Coleta de resíduos plásticos"
                    )
                }
            }

            // Texto informativo
            Text(
                text = "Você também pode entregar os resíduos plásticos em estabelecimentos perto de você, como restaurantes, mercearias etc.",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Estabelecimentos (duas caixas lado a lado)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Aumentei a altura para dar mais espaço aos cards
            ) {
                EstablishmentCard(
                    imageRes = R.drawable.cafeteria,
                    name = "Café Sustentável",
                    address = "Rua das Flores, 123",
                    isOpen = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .fillMaxHeight()
                )

                EstablishmentCard(
                    imageRes = R.drawable.restaurante,
                    name = "Restaurante Verde",
                    address = "Av. Principal, 456",
                    isOpen = false,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun EstablishmentCard(
    imageRes: Int,
    name: String,
    address: String,
    isOpen: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E7F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // Distribui o espaço verticalmente
        ) {
            // Imagem - Agora com tamanho maior e proporcional
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Maior altura para a imagem
                    .padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Dados do estabelecimento
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            // Endereço com ícone de localização
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Localização",
                    tint = Color(0xFF002140),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = address,
                    fontSize = 12.sp
                )
            }

            // Status (Aberto/Fechado)
            Text(
                text = if (isOpen) "Aberto" else "Fechado",
                color = if (isOpen) Color(0xFF388E3C) else Color(0xFFD32F2F),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPontosDeColetaScreen() {
    PontosDeColetaScreen()
}