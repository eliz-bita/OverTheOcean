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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.fiap.overtheocean.screens.ExtratoDePontosScreen
import br.com.fiap.overtheocean.screens.PontosDeColetaScreen
import br.com.fiap.overtheocean.ui.theme.OverTheOceanTheme
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import android.widget.Toast


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OverTheOceanTheme {
                NavegacaoApp()
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var tamanhoMaximo = 8
    var emailError by remember {
        mutableStateOf(false)
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Login",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2d347b)
        )
        Text(text = "Por favor entre com seus dados")

        Spacer(modifier = Modifier.height(48.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Imagem de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color(0xFF2d347b), shape = CircleShape)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(48.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (email.length > 0) emailError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Digite o seu e-mail")
                    },
                    isError = emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                )
                if(emailError){
                    Text(
                        text = "E-mail é obrigatório!",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Red,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        if(it.length <= tamanhoMaximo) password = it
                        if (password.length > 0) passwordError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Digite a sua senha")
                    },
                    isError = passwordError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                if(passwordError){
                    Text(
                        text = "Senha é obrigatória!",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Red,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        var hasError = false

                        if (email.isEmpty()) {
                            emailError = true
                            hasError = true
                        }

                        if (password.isEmpty()) {
                            passwordError = true
                            hasError = true
                        }

                        if (!hasError) {
                            navController.navigate("Feed")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2d347b)
                    )
                ) {
                    Text(
                        text = "ENTRAR",
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
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
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route
            if (currentRoute != "Login") {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Login") { LoginScreen(navController) }
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
            icon = {
                Icon(
                    Icons.Default.List,
                    contentDescription = "Extrato de Pontos",
                    tint = Color.White
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Pontos de Coleta") },
            label = { Text("Coleta", color = Color.White) },
            icon = {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Pontos de Coleta",
                    tint = Color.White
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Reportar") },
            label = { Text("Reportar", color = Color.White) },
            icon = {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Reportar",
                    tint = Color.White
                )
            }
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

        // Seção P.O.V. com imagens em círculos
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

            // Círculos com imagens em vez de cores sólidas
            // Primeiro círculo com imagem
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFD0D0D0), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.corais),
                    contentDescription = "Oceano e Corais",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Segundo círculo com imagem
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFD0D0D0), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mar),
                    contentDescription = "ondas",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Terceiro círculo com imagem
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFD0D0D0), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ocean),
                    contentDescription = "Oceano turquesa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
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
                            painter = painterResource(id = R.drawable.balu),
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
            // Círculo com imagem de perfil da Marina
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.marina),
                    contentDescription = "Marina profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

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
        ) {
            Image(
                painter = painterResource(id = R.drawable.marina),
                contentDescription = "Imagem de mergulhador",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

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
    // Importar o manipulador de URI para abrir links
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

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
            // Card de desconto em restaurantes (agora clicável)
            RewardCard(
                modifier = Modifier.weight(1f),
                title = "Desconto em restaurantes",
                backgroundColor = Color(0xFFF5F5F5),
                imageRes = R.drawable.restaurante,
                url = "https://www.cuponeria.com.br/cupom/restaurantes",
                onClick = { url ->
                    try {
                        uriHandler.openUri(url)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Não foi possível abrir o link", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            // Card de desconto em hotéis (agora clicável)
            RewardCard(
                modifier = Modifier.weight(1f),
                title = "Desconto em hotéis",
                backgroundColor = Color(0xFFF5F5F5),
                imageRes = R.drawable.cafeteria,
                url = "https://www.hoteis.com/lp/b/deals?locale=pt_BR&siteid=301800003&semcid=HCOM-BR.B.GOOGLE.BT-c-PT.GT&semdtl=a118251106433.b1142694981278.g1kwd-59581397076.e1c.m1Cj0KCQjwkN--BhDkARIsAD_mnIoJgZNTCUIoUPa4kprianVJxhL0HdgpAM_p1LsqpMjREFSDJ2iBqxcaAk28EALw_wcB.r13cc0bbdc9ea5ec7c24d786ca543002e3167642763b97db7ab4210c0a004ca14c.c1HET27WygITECCgETLD4hGg.j19196805.k1.d1624872249251.h1e.i1.l1.n1.o1.p1.q1.s1.t1.f1.u1.v1.w1&gad_source=1&gclid=Cj0KCQjwkN--BhDkARIsAD_mnIoJgZNTCUIoUPa4kprianVJxhL0HdgpAM_p1LsqpMjREFSDJ2iBqxcaAk28EALw_wcB",
                onClick = { url ->
                    try {
                        uriHandler.openUri(url)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Não foi possível abrir o link", Toast.LENGTH_SHORT).show()
                    }
                }
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
    backgroundColor: Color,
    imageRes: Int,
    url: String = "",  // Parâmetro de URL com valor padrão vazio
    onClick: (String) -> Unit = {}  // Função de callback com implementação padrão vazia
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .let {
                if (url.isNotEmpty()) {
                    it.clickable { onClick(url) }
                } else {
                    it
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column {
            // Imagem ocupando parte superior do card
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )

            // Texto na parte inferior do card
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
}

@Composable
fun ExtratoDePontosScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Extrato de Pontos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF436cff)
        )
    }
}

@Composable
fun PontosDeColetaScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pontos de Coleta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF436cff)
        )
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
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Campo vazio, normalmente teria um dropdown aqui
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