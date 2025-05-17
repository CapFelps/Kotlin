package br.com.fiap.softekfiap.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import com.google.gson.Gson

@Composable
fun ReviewQuestionnaireSummary(entry: QuestionnaireEntry) {
    val respostas = remember(entry) {
        Gson().fromJson(entry.respostasJson, Map::class.java)
    }
    Text("Seu último questionário foi em: ${entry.date}")
    // Aqui você pode expandir mostrando respostas detalhadas se quiser
}
