package br.com.fiap.softekfiap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questionnaire_entries")
data class QuestionnaireEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int?,
    val respostasJson: String, // JSON com todas as respostas
    val date: String
)
