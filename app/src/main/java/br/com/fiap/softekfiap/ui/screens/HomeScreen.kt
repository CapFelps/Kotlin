package br.com.fiap.softekfiap.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.softekfiap.data.AppDatabase
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import br.com.fiap.softekfiap.ui.components.ReviewQuestionnaireSummary
import br.com.fiap.softekfiap.util.SessionManager
import br.com.fiap.softekfiap.util.podeResponderQuestionario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

@Composable
fun HomeScreen(navController: NavController, userId: Int?) {
    var selectedIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val username = SessionManager.getUsername(context) ?: "usuário"
    val tabs = listOf("Início", "Questionário", "Histórico")

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            // Limpa a sessão e volta para login
                            SessionManager.limparSessao(context)
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Sair",
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        "Sair",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigation {
                tabs.forEachIndexed { index, title ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text(title) },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (selectedIndex) {
                    0 -> HomeTab(username)
                    1 -> QuestionnaireTab(navController)
                    2 -> HistoryTab(navController)
                }
            }
        }
    }
}

@Composable
fun HomeTab(username: String?) {
    Text("Olá, $username! Seja bem-vindo ao seu espaço de bem-estar 🧠", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(16.dp))
    Text("Use o menu abaixo para acessar as funções.")
}

@Composable
fun QuestionnaireTab(navController: NavController) {

    val context = LocalContext.current
    val userId = SessionManager.getIdUsuario(context)

    var entriesThisMonth by remember { mutableStateOf<List<QuestionnaireEntry>>(emptyList()) }
    var canAnswer by remember { mutableStateOf<Boolean?>(null) }
    var lastEntry by remember { mutableStateOf<QuestionnaireEntry?>(null) }
    var showReview by remember { mutableStateOf(false) }


    LaunchedEffect(userId) {
        withContext(Dispatchers.IO) {
            val dao = AppDatabase.getInstance(context).questionnaireDao()
            val entries = dao.getEntriesThisMonth(userId, LocalDate.now().toString())
            entriesThisMonth = entries
            lastEntry = entries.firstOrNull()
            canAnswer = podeResponderQuestionario(entries)
        }
    }

    if (canAnswer == null) {
        CircularProgressIndicator()
    } else if (canAnswer == true) {
        Button(onClick = { navController.navigate("questionnaire") }) {
            Text("Responder questionário")
        }
    } else {
        lastEntry?.let { entry ->
            if (showReview) {
                QuestionnaireReviewOnly(
                    respostasJson = entry.respostasJson,
                    date = entry.date
                )
            } else {
                ReviewQuestionnaireSummary(entry)
                Text(
                    "Você já respondeu o questionário recentemente. Retorne daqui a alguns dias.",
                    color = Color.Gray, modifier = Modifier.padding(top = 12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { navController.navigate("historico") }) {
                    Text("Ver histórico de respostas")
                }
            }
        }
    }
}

@Composable
fun HistoryTab(navController: NavController) {
    HistoryScreen(navController)
}