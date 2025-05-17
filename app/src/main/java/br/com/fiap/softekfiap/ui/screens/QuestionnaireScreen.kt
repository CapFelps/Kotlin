package br.com.fiap.softekfiap.ui.screens

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softekfiap.viewmodel.QuestionnaireDataViewModel

@Composable
fun QuestionnaireScreen(navController: NavController) {
    val viewModel: QuestionnaireDataViewModel = viewModel()
    var step by remember { mutableStateOf(1) }

    when (step) {
        1 -> TopicCargaDeTrabalhoScreen(navController) { respostas ->
            viewModel.saveCargaDeTrabalho(respostas)
            step = 2
        }
        2 -> TopicSinaisDeAlertaScreen(navController) { respostas ->
            viewModel.saveSinaisDeAlerta(respostas)
            step = 3
        }
        3 -> TopicClimaRelacionamentoScreen(navController) { respostas ->
            viewModel.saveClimaRelacionamento(respostas)
            step = 4
        }
        4 -> TopicComunicacaoScreen(navController) { respostas ->
            viewModel.saveComunicacao(respostas)
            step = 5
        }
        5 -> TopicRelacaoLiderancaScreen(navController) { respostas ->
            viewModel.saveRelacaoLideranca(respostas)
            step = 6
        }
        6 -> QuestionnaireReviewScreen(navController, viewModel)
    }
}
