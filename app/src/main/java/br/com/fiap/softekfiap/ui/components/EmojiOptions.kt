package br.com.fiap.softekfiap.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmojiOption(
    emoji: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onClick,
            modifier = Modifier.size(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selected) MaterialTheme.colors.primary
                else MaterialTheme.colors.surface
            )
        ) {
            Text(emoji, fontSize = 24.sp)
        }
        Text(label, fontSize = 12.sp)
    }
}
