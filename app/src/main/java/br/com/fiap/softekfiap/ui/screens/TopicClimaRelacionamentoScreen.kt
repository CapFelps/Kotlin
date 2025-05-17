package br.com.fiap.softekfiap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.softekfiap.ui.components.ScaleSelector // <<< import do componente

@Composable
fun TopicClimaRelacionamentoScreen(
    navController: NavController,
    onSubmit: (Map<String, Int>) -> Unit // idPergunta -> resposta 1..5
) {
    val perguntas = listOf(
        "Como está o seu relacionamento com seu chefe numa escala de 1 a 5? (Sendo 01 - ruim e 05 - Ótimo)" to "relacionamento1",
        "Como está o seu relacionamento com seus colegas de trabalho numa escala de 1 a 5? (Sendo 01 - ruim e 05 - Ótimo)" to "relacionamento2",
        "Sinto que sou tratado(a) com respeito pelos meus colegas de trabalho." to "relacionamento3",
        "Consigo me relacionar de forma saudável e colaborativa com minha equipe." to "relacionamento4",
        "Tenho liberdade para expressar minhas opiniões sem medo de retaliações." to "relacionamento5",
        "Me sinto acolhido(a) a parte do time onde trabalho." to "relacionamento6",
        "Sinto que existe espírito de cooperação entre os colaboradores." to "relacionamento7"
    )

    val respostas = remember { mutableStateMapOf<String, Int>() }
    val podeAvancar = perguntas.all { respostas[it.second] != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Diagnóstico de Clima - Relacionamento",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        perguntas.forEach { (texto, id) ->
            Text(texto, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            ScaleSelector(
                selectedValue = respostas[id],
                onSelect = { respostas[id] = it }
            )
            Spacer(Modifier.height(18.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onSubmit(respostas) },
            enabled = podeAvancar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Próximo")
        }
    }
}
