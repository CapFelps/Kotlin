package br.com.fiap.softekfiap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TopicSinaisDeAlertaScreen(
    navController: NavController,
    onSubmit: (Map<String, String>) -> Unit // idPergunta -> resposta (string)
) {
    // Perguntas do tópico
    val perguntas = listOf(
        "Você tem apresentado sintomas como insônia, irritabilidade ou cansaço extremo?" to "sinais1",
        "Você sente que sua saúde mental prejudica sua produtividade no trabalho?" to "sinais2"
    )

    // Opções de resposta
    val opcoes = listOf("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre")

    // Respostas do usuário
    val respostas = remember { mutableStateMapOf<String, String>() }
    val podeAvancar = perguntas.all { respostas[it.second] != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Sinais de Alerta",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        perguntas.forEach { (texto, id) ->
            Text(texto, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Column {
                opcoes.forEach { opcao ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = respostas[id] == opcao,
                                onClick = { respostas[id] = opcao }
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = respostas[id] == opcao,
                            onClick = { respostas[id] = opcao }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(opcao)
                    }
                }
            }
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
