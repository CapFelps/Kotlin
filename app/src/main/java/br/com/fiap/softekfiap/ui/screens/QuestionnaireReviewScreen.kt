package br.com.fiap.softekfiap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import br.com.fiap.softekfiap.util.SessionManager
import br.com.fiap.softekfiap.viewmodel.QuestionnaireDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.gson.Gson
import java.time.LocalDate

@Composable
fun QuestionnaireReviewScreen(
    navController: NavController,
    viewModel: QuestionnaireDataViewModel,
) {
    val respostas by viewModel.questionnaireData.collectAsState()
    var enviado by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val questionnaireDao = AppDatabase.getInstance(context).questionnaireDao()
    val gson = remember { Gson() }
    val today = LocalDate.now().toString()
    val userId = SessionManager.getIdUsuario(context)

    if (enviado) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Suas respostas foram enviadas, obrigado!",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
        }
        LaunchedEffect(Unit) {
            delay(2000)
            navController.navigate("home") {
                popUpTo("questionnaire") { inclusive = true }
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
        ) {
            Text(
                "Confira suas respostas",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            ReviewSection(
                title = "Fatores de Carga de Trabalho",
                questions = listOf(
                    "Como você avalia sua carga de trabalho?" to respostas.cargaDeTrabalho["carga1"],
                    "Sua carga de trabalho afeta sua qualidade de vida?" to respostas.cargaDeTrabalho["carga2"],
                    "Você trabalha além do seu horário regular?" to respostas.cargaDeTrabalho["carga3"]
                )
            )

            ReviewSection(
                title = "Sinais de Alerta",
                questions = listOf(
                    "Você tem apresentado sintomas como insônia, irritabilidade ou cansaço extremo?" to respostas.sinaisDeAlerta["sinais1"],
                    "Você sente que sua saúde mental prejudica sua produtividade no trabalho?" to respostas.sinaisDeAlerta["sinais2"]
                )
            )

            ReviewSection(
                title = "Clima – Relacionamento",
                questions = listOf(
                    "Como está o seu relacionamento com seu chefe numa escala de 1 a 5? (01 - ruim, 05 - ótimo)" to respostas.climaRelacionamento["relacionamento1"]?.toString(),
                    "Como está o seu relacionamento com seus colegas de trabalho numa escala de 1 a 5? (01 - ruim, 05 - ótimo)" to respostas.climaRelacionamento["relacionamento2"]?.toString(),
                    "Sinto que sou tratado(a) com respeito pelos meus colegas de trabalho." to respostas.climaRelacionamento["relacionamento3"]?.toString(),
                    "Consigo me relacionar de forma saudável e colaborativa com minha equipe." to respostas.climaRelacionamento["relacionamento4"]?.toString(),
                    "Tenho liberdade para expressar minhas opiniões sem medo de retaliações." to respostas.climaRelacionamento["relacionamento5"]?.toString(),
                    "Me sinto acolhido(a) a parte do time onde trabalho." to respostas.climaRelacionamento["relacionamento6"]?.toString(),
                    "Sinto que existe espírito de cooperação entre os colaboradores." to respostas.climaRelacionamento["relacionamento7"]?.toString()
                )
            )

            ReviewSection(
                title = "Comunicação",
                questions = listOf(
                    "Recebo orientações claras e objetivas sobre minhas atividades e responsabilidades." to respostas.comunicacao["comunicacao1"]?.toString(),
                    "Sinto que posso me comunicar abertamente com minha liderança." to respostas.comunicacao["comunicacao2"]?.toString(),
                    "As informações importantes circulam de forma eficiente dentro da empresa." to respostas.comunicacao["comunicacao3"]?.toString(),
                    "Tenho clareza sobre as metas e os resultados esperados de mim." to respostas.comunicacao["comunicacao4"]?.toString()
                )
            )

            ReviewSection(
                title = "Relação com a Liderança",
                questions = listOf(
                    "Minha liderança demonstra interesse pelo meu bem-estar no trabalho." to respostas.relacaoLideranca["lideranca1"]?.toString(),
                    "Minha liderança está disponível para me ouvir quando necessário." to respostas.relacaoLideranca["lideranca2"]?.toString(),
                    "Me sinto confortável para reportar problemas ou dificuldades ao meu líder." to respostas.relacaoLideranca["lideranca3"]?.toString(),
                    "Minha liderança reconhece minhas entregas e esforços." to respostas.relacaoLideranca["lideranca4"]?.toString(),
                    "Existe confiança e transparência na relação com minha liderança." to respostas.relacaoLideranca["lideranca5"]?.toString()
                )
            )

            Spacer(Modifier.height(28.dp))
            Button(
                onClick = {
                    // Serializa e salva no banco local com o userId
                    val respostasJson = gson.toJson(respostas)
                    CoroutineScope(Dispatchers.IO).launch {
                        questionnaireDao.insert(
                            QuestionnaireEntry(
                                userId = userId, // <- Passa aqui!
                                respostasJson = respostasJson,
                                date = today
                            )
                        )
                    }
                    enviado = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text("Enviar Respostas", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ReviewSection(
    title: String,
    questions: List<Pair<String, String?>>
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            questions.forEach { (pergunta, resposta) ->
                if (!resposta.isNullOrBlank()) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            pergunta,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            resposta,
                            fontSize = 15.sp,
                            color = Color(0xFF388E3C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    color = Color(0x1A388E3C),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}
