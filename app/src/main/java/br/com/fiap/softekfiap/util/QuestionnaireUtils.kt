package br.com.fiap.softekfiap.util

import br.com.fiap.softekfiap.model.QuestionnaireEntry
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun podeResponderQuestionario(entriesThisMonth: List<QuestionnaireEntry>): Boolean {
    if (entriesThisMonth.size >= 10) return false
    if (entriesThisMonth.isEmpty()) return true

    val lastDate = LocalDate.parse(entriesThisMonth.first().date)
    val today = LocalDate.now()
    val daysDiff = ChronoUnit.DAYS.between(lastDate, today)
    return daysDiff >= 3
}
