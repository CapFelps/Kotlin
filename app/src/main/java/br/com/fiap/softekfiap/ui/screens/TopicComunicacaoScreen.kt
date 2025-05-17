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
import br.com.fiap.softekfiap.ui.components.ScaleSelector

@Composable
fun TopicComunicacaoScreen(
    navController: NavController,
    onSubmit: (Map<String, Int>) -> Unit // idPergunta -> resposta 1..5
) {
    val perguntas = listOf(
        "Recebo orientações claras e objetivas sobre minhas atividades e responsabilidades." to "comunicacao1",
        "Sinto que posso me comunicar abertamente com minha liderança." to "comunicacao2",
        "As informações importantes circulam de forma eficiente dentro da empresa." to "comunicacao3",
        "Tenho clareza sobre as metas e os resultados esperados de mim." to "comunicacao4"
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
            "Comunicação",
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