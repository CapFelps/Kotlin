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
fun TopicRelacaoLiderancaScreen(
    navController: NavController,
    onSubmit: (Map<String, Int>) -> Unit // idPergunta -> resposta 1..5
) {
    val perguntas = listOf(
        "Minha liderança demonstra interesse pelo meu bem-estar no trabalho" to "lideranca1",
        "Minha liderança está disponível para me ouvir quando necessário." to "lideranca2",
        "Me sinto confortável para reportar problemas ou dificuldades ao meu líder" to "lideranca3",
        "Minha liderança reconhece minhas entregas e esforços" to "lideranca4",
        "Existe confiança e transparência na relação com minha liderança" to "lideranca5"
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
            "Relação com a Liderança",
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
