package br.com.fiap.overtheocean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    val navController = rememberNavController()  // Controlador de navegação

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)  // Passa o controlador de navegação para o BottomNavigationBar
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
            icon = { Icon(Icons.Default.Home, contentDescription = "Feed", tint = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF2d347b)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Pontos") },
            label = { Text("Pontos", color = Color.White) },
            icon = { Icon(Icons.Default.Star, contentDescription = "Pontos", tint = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF2d347b)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Extrato de Pontos") },
            label = { Text("Extrato", color = Color.White) },
            icon = { Icon(Icons.Default.List, contentDescription = "Extrato de Pontos", tint = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF2d347b)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Pontos de Coleta") },
            label = { Text("Coleta", color = Color.White) },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Pontos de Coleta", tint = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF2d347b)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Reportar") },
            label = { Text("Reportar", color = Color.White) },
            icon = { Icon(Icons.Default.Warning, contentDescription = "Reportar", tint = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF2d347b)
            )
        )
    }
}
@Composable
fun FeedScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Feed", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF436cff))
    }
}

@Composable
fun PontosScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Pontos", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF436cff))
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Reportar Problemas", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF436cff))
    }
}