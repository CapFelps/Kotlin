package br.com.fiap.softekfiap.ui.screens

import com.google.gson.Gson
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.softekfiap.data.AppDatabase
import br.com.fiap.softekfiap.model.EmotionEntry
import br.com.fiap.softekfiap.ui.components.EmojiOptionsGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CheckinScreen(navController: NavController, userId: Int?) {
    val context = LocalContext.current
    val emotionDao = AppDatabase.getInstance(context).emotionDao()
    val today = LocalDate.now().toString()
    val gson = Gson()
    val scope = rememberCoroutineScope()

    if (userId == null) {
        Text("Erro: usuário não autenticado")
        return
    }

    var alreadyCheckedIn by remember { mutableStateOf<Boolean?>(null) }

    // Verifica se já respondeu hoje
    LaunchedEffect(userId) {
        val count = emotionDao.verificaCheckIn(userId, today)
        alreadyCheckedIn = count > 0
    }

    // Carregando
    if (alreadyCheckedIn == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Já respondeu hoje → vai para Home direto
    if (alreadyCheckedIn == true) {
        LaunchedEffect(Unit) {
            navController.navigate("home/$userId") {
                popUpTo("checkin") { inclusive = true }
            }
        }
        return
    }

    // Fluxo normal de check-in
    var step by remember { mutableStateOf(1) }
    var selectedEmoji by remember { mutableStateOf<Pair<String, String>?>(null) }
    var selectedFeeling by remember { mutableStateOf<String?>(null) }

    val emotionOptions = listOf(
        "Motivado", "Cansado", "Preocupado",
        "Estressado", "Animado", "Satisfeito"
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (step == 1) {
                Text("Escolha o seu emoji de hoje?", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))

                EmojiOptionsGroup(
                    selectedEmoji = selectedEmoji,
                    onEmojiSelected = { selectedEmoji = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { if (selectedEmoji != null) step = 2 },
                    enabled = selectedEmoji != null
                ) {
                    Text("Confirmar")
                }

            } else {
                Text("Como você se sente hoje?", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))

                emotionOptions.chunked(2).forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach { emotion ->
                            Button(
                                onClick = {
                                    selectedFeeling = emotion

                                    scope.launch {
                                        val replyCheckin = mapOf(
                                            "descricao_emoji" to (selectedEmoji?.second ?: ""),
                                            "emoji" to (selectedEmoji?.first ?: ""),
                                            "sentimento" to emotion,
                                            "data" to today
                                        )

                                        val replyCheckinJson = gson.toJson(replyCheckin)

                                        val entry = EmotionEntry(
                                            userId = userId,
                                            replyCheckin = replyCheckinJson,
                                            date = today
                                        )

                                        emotionDao.insert(entry)

                                        Toast.makeText(
                                            context,
                                            "Check-in registrado com sucesso!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        navController.navigate("home?userId=$userId") {
                                            popUpTo("checkin?userId=$userId") { inclusive = true }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .weight(1f)
                            ) {
                                Text(emotion)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { step = 1 }) {
                    Text("Voltar")
                }
            }
        }
    }
}
