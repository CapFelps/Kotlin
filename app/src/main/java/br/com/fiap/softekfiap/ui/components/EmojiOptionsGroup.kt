package br.com.fiap.softekfiap.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmojiOptionsGroup(
    selectedEmoji: Pair<String, String>?,
    onEmojiSelected: (Pair<String, String>) -> Unit
) {
    val emojiOptions = listOf(
        "😢" to "Triste",
        "😄" to "Alegre",
        "😴" to "Cansado",
        "😰" to "Ansioso",
        "😨" to "Medo",
        "😠" to "Raiva"
    )

    emojiOptions.chunked(3).forEach { row ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            row.forEach { (emoji, label) ->
                EmojiOption(
                    emoji = emoji,
                    label = label,
                    selected = selectedEmoji?.first == emoji,
                    onClick = { onEmojiSelected(emoji to label) }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
