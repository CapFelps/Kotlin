package br.com.fiap.softekfiap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_in")
data class EmotionEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int?, // Inteiro para alinhar com a base do colega
    val replyCheckin: String, // JSON serializado com dados do check-in
    val date: String
)
