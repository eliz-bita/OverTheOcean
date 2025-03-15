package br.com.fiap.overtheocean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.overtheocean.ui.theme.OverTheOceanTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OverTheOceanTheme {
                // Tela base de navegação
                NavegacaoApp()
            }
        }
    }
}

@Composable
fun NavegacaoApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Feed",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Feed") { FeedScreen() }
            composable("Pontos") { PontosScreen() }
            composable("Extrato de Pontos") { ExtratoDePontosScreen() }
            composable("Pontos de Coleta") { PontosDeColetaScreen() }
            composable("Reportar") { ReportarScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFF2d347b),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Feed") },
            label = { Text("Feed", color = Color.White) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Feed", tint = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Pontos") },
            label = { Text("Pontos", color = Color.White) },
            icon = { Icon(Icons.Default.Star, contentDescription = "Pontos", tint = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Extrato de Pontos") },
            label = { Text("Extrato", color = Color.White) },
            icon = { Icon(Icons.Default.List, contentDescription = "Extrato de Pontos", tint = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Pontos de Coleta") },
            label = { Text("Coleta", color = Color.White) },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Pontos de Coleta", tint = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Reportar") },
            label = { Text("Reportar", color = Color.White) },
            icon = { Icon(Icons.Default.Warning, contentDescription = "Reportar", tint = Color.White) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController)
}

@Composable
fun FeedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Cabeçalho
        Text(
            text = "Over the Ocean",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Seção P.O.V. com círculos
        Text(
            text = "P.O.V.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Círculo com símbolo de adição
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE6EFFE), CircleShape)
                    .border(1.dp, Color(0xFFD0D0D0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar",
                    tint = Color.Blue
                )
            }

            // Círculos com imagens de oceano
            CircleImage(color = Color(0xFF1E6799))
            CircleImage(color = Color(0xFFACC8D7))
            CircleImage(color = Color(0xFFB8E1F0))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card do mascote com a mensagem
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Mascote Balu
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFFE0F4FF), CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "Balu mascote",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Mensagem e nome do mascote
                    Column {
                        Text(
                            text = "Jogar o lixo no lugar certo faz toda a diferença para a vida marinha.",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Balu",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Mascote do OverTheOcean",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Seção Marina com imagem submarina
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            // Círculo com imagem azul do oceano
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color(0xFF1E6799), CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Marina",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Imagem grande de mergulhador
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E6799))
        ) {
            // Aqui você adicionaria a imagem real do mergulhador
            // Por enquanto, usamos apenas um placeholder azul
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Silhueta do mergulhador (simplificada)
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 80.dp)
                        .background(Color.Black.copy(alpha = 0.5f))
                )
            }
        }
    }
}

// Componente auxiliar para os círculos de imagem
@Composable
fun CircleImage(color: Color) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color, CircleShape)
            .border(1.dp, Color(0xFFD0D0D0), CircleShape)
    )
}

@Composable
fun PontosScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título da tela
        Text(
            text = "Pontuação",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E4374),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Linha de progresso com pontos
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Linha de fundo
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                color = Color.LightGray,
                thickness = 2.dp
            )

            // Pontos na linha de progresso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProgressPoint("50pts")
                ProgressPoint("100pts")
                ProgressPoint("250pts")
                ProgressPoint("500pts")
                ProgressPoint("1000pts")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Medidor circular
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Círculo de fundo (cinza)
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round)
                )
            }

            // Círculo de progresso (azul)
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color(0xFF3866E6),
                    startAngle = 135f,
                    sweepAngle = 270f, // Completo para 1000 pontos
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round)
                )
            }

            // Pequena marca no fim do progresso
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.Black,
                    startAngle = 135f + 270f,
                    sweepAngle = 1f,
                    useCenter = false,
                    style = Stroke(width = 22f, cap = StrokeCap.Round)
                )
            }

            // Texto central (pontos)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Seus pontos",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "1.000",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Seção de recompensas
        Text(
            text = "Recompensas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2E4374),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        )

        // Cards de recompensas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card de desconto em restaurantes
            RewardCard(
                modifier = Modifier.weight(1f),
                title = "Desconto em restaurantes",
                backgroundColor = Color(0xFFF5F5F5)
            )

            // Card de desconto em hotéis
            RewardCard(
                modifier = Modifier.weight(1f),
                title = "Desconto em hotéis",
                backgroundColor = Color(0xFFF5F5F5)
            )
        }
    }
}

@Composable
fun ProgressPoint(text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(Color.LightGray, CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun RewardCard(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color
) {
    Card(
        modifier = modifier
            .height(120.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(8.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun ExtratoDePontosScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Extrato de Pontos", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF436cff))
    }
}

@Composable
fun PontosDeColetaScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Pontos de Coleta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF436cff))
    }
}

@Composable
fun ReportarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Título
        Text(
            text = "Reportar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E4374),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Pergunta "O que você viu?"
        Text(
            text = "O que você viu?",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Dropdown de seleção
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFFEEF1FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Selecione o tipo",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Selecionar",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de descrição
        Text(
            text = "Descreva aqui o que viu",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Campo de texto
            OutlinedCard(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFFEEF1FA))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Campo vazio, normalmente teria um TextField aqui
                }
            }

            // Ícone de ajuda
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Informação",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pergunta sobre foto
        Text(
            text = "Você tem uma foto do ocorrido?",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Botão de upload
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFFEEF1FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Escolha uma imagem",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Anexar arquivo",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botão de continuar
        Button(
            onClick = { /* Implementar ação */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Continuar",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        // Espaço para a barra de navegação inferior
        Spacer(modifier = Modifier.height(56.dp))
    }
}
