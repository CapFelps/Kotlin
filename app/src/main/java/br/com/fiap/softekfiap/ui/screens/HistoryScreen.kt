package br.com.fiap.softekfiap.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.softekfiap.data.AppDatabase
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import br.com.fiap.softekfiap.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext


@Composable
fun HistoryScreen(navController: NavController) {
    val context = LocalContext.current
    val userId = SessionManager.getIdUsuario(context)
    var historico by remember { mutableStateOf<List<QuestionnaireEntry>>(emptyList()) }
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    // Carrega o histórico
    LaunchedEffect(userId) {
        withContext(Dispatchers.IO) {
            val dao = AppDatabase.getInstance(context).questionnaireDao()
            val entries = dao.getAllByUser(userId).firstOrNull() ?: emptyList()
            historico = entries
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                "Histórico de Questionários",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (historico.isEmpty()) {
            item {
                Text("Nenhum questionário enviado ainda.", color = Color.Gray)
            }
        } else {
            itemsIndexed(historico) { idx, entry ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickable { expandedIndex = if (expandedIndex == idx) null else idx }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Envio do dia: ${entry.date}", fontWeight = FontWeight.Bold)
                        if (expandedIndex == idx) {
                            QuestionnaireReviewOnly(entry.respostasJson, entry.date)
                        }
                    }
                }
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar")
            }
        }
    }
}