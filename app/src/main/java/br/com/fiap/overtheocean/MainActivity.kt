package br.com.fiap.overtheocean

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import br.com.fiap.overtheocean.model.Relatorio
import br.com.fiap.overtheocean.model.TipoRelatorio
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.CheckCircle


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
            composable("Feed") { FeedScreen(navController) }
            composable("Pontos") { PontosScreen() }
            composable("Extrato de Pontos") { ExtratoDePontosScreen() }
            composable("Pontos de Coleta") { PontosDeColetaScreen() }
            composable("Reportar") { ReportarScreen<Any>() }
        }
    }
}

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
fun FeedScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Over the Ocean",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

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
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE6EFFE), CircleShape)
                    .border(1.dp, Color(0xFFD0D0D0), CircleShape)
                    .clickable { navController.navigate("Reportar") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar",
                    tint = Color.Blue
                )
            }

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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
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
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pontuação",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E4374),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                color = Color.LightGray,
                thickness = 2.dp
            )

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

        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round)
                )
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color(0xFF3866E6),
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round)
                )
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.Black,
                    startAngle = 135f + 270f,
                    sweepAngle = 1f,
                    useCenter = false,
                    style = Stroke(width = 22f, cap = StrokeCap.Round)
                )
            }

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

        Text(
            text = "Recompensas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2E4374),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
    url: String = "",
    onClick: (String) -> Unit = {}
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

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )


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
fun <T> ReportarScreen(
    onSalvarClick: (Relatorio) -> Unit = {}
) {
    var tipoSelecionado by remember { mutableStateOf<br.com.fiap.overtheocean.model.TipoRelatorio?>(null) }
    var descricao by remember { mutableStateOf("") }
    var caminhoImagem by remember { mutableStateOf<Uri?>(null) }
    var imagemSelecionada by remember { mutableStateOf<ImageBitmap?>(null) }
    var mostrarConfirmacao by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            caminhoImagem = it
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                    .asImageBitmap()
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, it).asImageBitmap()
            }
            imagemSelecionada = bitmap
        }
    }

    val botaoAtivo = tipoSelecionado != null && descricao.isNotEmpty() && caminhoImagem != null

    // Verifica se deve mostrar a tela de confirmação
    if (mostrarConfirmacao) {
        TelaConfirmacao(
            onVoltar = {
                // Retorna para a tela principal
                mostrarConfirmacao = false
                // Limpa os campos do formulário
                tipoSelecionado = null
                descricao = ""
                caminhoImagem = null
                imagemSelecionada = null
            }
        )
    } else {
        // Tela principal de relatório
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Reportar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E4374),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "O que você viu?",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            br.com.fiap.overtheocean.model.TipoRelatorio.values().forEach { tipo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { tipoSelecionado = tipo },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = tipoSelecionado == tipo,
                        onClick = { tipoSelecionado = tipo },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF2E4374)
                        )
                    )
                    Text(
                        text = tipo.descricao,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Descreva aqui o que viu (máximo 200 caracteres)",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = descricao,
                onValueChange = {
                    if (it.length <= 200) descricao = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Digite sua descrição aqui") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFEEF1FA),
                    focusedContainerColor = Color(0xFFEEF1FA),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF2E4374)
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                maxLines = 5
            )

            Text(
                text = "${descricao.length}/200",
                fontSize = 12.sp,
                color = if (descricao.length >= 180) Color.Red else Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Você tem uma foto do ocorrido?",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { launcher.launch("image/*") },
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
                        text = if (caminhoImagem == null) "Escolha uma imagem" else "Imagem selecionada",
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

            if (imagemSelecionada != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    bitmap = imagemSelecionada!!,
                    contentDescription = "Preview da imagem",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (botaoAtivo) {
                        val relatorio = Relatorio(
                            tipo = tipoSelecionado!!,
                            descricao = descricao,
                            caminhoImagem = caminhoImagem.toString()
                        )
                        onSalvarClick(relatorio)
                        mostrarConfirmacao = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (botaoAtivo) Color(0xFF2E4374) else Color(0xFFE0E0E0),
                    disabledContainerColor = Color(0xFFE0E0E0)
                ),
                enabled = botaoAtivo,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Continuar",
                    fontSize = 16.sp,
                    color = if (botaoAtivo) Color.White else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@Composable
fun TelaConfirmacao(onVoltar: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Sucesso",
                tint = Color(0xFF2E4374),
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Relatório enviado com sucesso!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E4374),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Seu relatório foi recebido e será analisado pela nossa equipe. Obrigado por contribuir para a preservação dos oceanos.",
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onVoltar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E4374)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Voltar para o início",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}