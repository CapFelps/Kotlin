package br.com.fiap.softekfiap.data

data class QuestionnaireData(
    val cargaDeTrabalho: Map<String, String> = emptyMap(),
    val sinaisDeAlerta: Map<String, String> = emptyMap(),
    val climaRelacionamento: Map<String, Int> = emptyMap(),
    val comunicacao: Map<String, Int> = emptyMap(),
    val relacaoLideranca: Map<String, Int> = emptyMap()
)
