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
fun TopicCargaDeTrabalhoScreen(
    navController: NavController,
    onSubmit: (Map<String, String>) -> Unit // idPergunta -> resposta (string)
) {
    // Associe cada pergunta à sua lista de opções
    val perguntasComOpcoes = listOf(
        Triple(
            "Como você avalia sua carga de trabalho?",
            "carga1",
            listOf("Muito Leve", "Leve", "Média", "Alta", "Muito Alta")
        ),
        Triple(
            "Sua carga de trabalho afeta sua qualidade de vida?",
            "carga2",
            listOf("Não", "Raramente", "Às Vezes", "Frequentemente", "Sempre")
        ),
        Triple(
            "Você trabalha além do seu horário regular?",
            "carga3",
            listOf("Não", "Raramente", "Às Vezes", "Frequentemente", "Sempre")
        )
    )

    val respostas = remember { mutableStateMapOf<String, String>() }
    val podeAvancar = perguntasComOpcoes.all { respostas[it.second] != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Fatores de Carga de Trabalho",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        perguntasComOpcoes.forEach { (texto, id, opcoes) ->
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
