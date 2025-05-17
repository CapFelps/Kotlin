package br.com.fiap.softekfiap.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.softekfiap.data.QuestionnaireData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuestionnaireDataViewModel : ViewModel() {
    private val _questionnaireData = MutableStateFlow(QuestionnaireData())
    val questionnaireData: StateFlow<QuestionnaireData> = _questionnaireData

    fun saveCargaDeTrabalho(respostas: Map<String, String>) {
        _questionnaireData.value = _questionnaireData.value.copy(cargaDeTrabalho = respostas)
    }
    fun saveSinaisDeAlerta(respostas: Map<String, String>) {
        _questionnaireData.value = _questionnaireData.value.copy(sinaisDeAlerta = respostas)
    }
    fun saveClimaRelacionamento(respostas: Map<String, Int>) {
        _questionnaireData.value = _questionnaireData.value.copy(climaRelacionamento = respostas)
    }
    fun saveComunicacao(respostas: Map<String, Int>) {
        _questionnaireData.value = _questionnaireData.value.copy(comunicacao = respostas)
    }
    fun saveRelacaoLideranca(respostas: Map<String, Int>) {
        _questionnaireData.value = _questionnaireData.value.copy(relacaoLideranca = respostas)
    }

    // Se quiser um reset:
    fun clear() {
        _questionnaireData.value = QuestionnaireData()
    }
}
