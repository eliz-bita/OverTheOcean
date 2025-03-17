package br.com.fiap.overtheocean.screens

import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import br.com.fiap.overtheocean.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PontosDeColetaScreen() {
    val context = LocalContext.current
    var permissionGranted by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Verifique se a permissão já foi concedida
    LaunchedEffect(Unit) {
        permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Launcher para solicitar permissão
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
    }

    // Solicitar permissão ao iniciar, se ainda não foi concedida
    LaunchedEffect(Unit) {
        if (!permissionGranted) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold { innerPadding ->
        // Aplicando scroll apenas à Column principal, não afetando a barra de navegação
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )

            // Novo Card com Mapa - com padding horizontal para evitar que encoste nas bordas
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(250.dp) // Aumentei a altura para melhor visualização
            ) {
                val initialPosition = LatLng(-23.55052, -46.633308) // São Paulo como exemplo
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(initialPosition, 12f)
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    // Verifica se temos permissão antes de mostrar o mapa
                    if (permissionGranted) {
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
                    } else {
                        // Exibir mensagem quando não há permissão
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "É necessário conceder permissão de localização para visualizar o mapa",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            // Texto informativo
            Text(
                text = "Você também pode entregar os resíduos plásticos em estabelecimentos perto de você, como restaurantes, mercearias etc.",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
            )

            // Estabelecimentos (duas caixas lado a lado) com altura fixa para evitar compressão
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Primeira linha de cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Altura fixa para os cards
                        .padding(bottom = 16.dp)
                ) {
                    EstablishmentCard(
                        imageRes = R.drawable.cafeteria,
                        name = "Café Sustentável",
                        address = "Rua das Flores, 123",
                        isOpen = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    EstablishmentCard(
                        imageRes = R.drawable.restaurante,
                        name = "Restaurante Verde",
                        address = "Av. Principal, 456",
                        isOpen = false,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }

                // Opcionalmente, você pode adicionar mais cards em outra linha
                // Row(
                //     modifier = Modifier
                //         .fillMaxWidth()
                //         .height(200.dp)
                // ) {
                //     EstablishmentCard(...)
                //     EstablishmentCard(...)
                // }
            }

            // Espaço no final para garantir que tudo seja visível após o scroll
            Spacer(modifier = Modifier.height(80.dp))
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
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E7F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagem - com tamanho proporcional
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Usa peso em vez de altura fixa
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
                fontSize = 14.sp,
                maxLines = 1
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
                    fontSize = 12.sp,
                    maxLines = 1
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