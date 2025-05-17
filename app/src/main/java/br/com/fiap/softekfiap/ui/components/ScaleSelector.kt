package br.com.fiap.softekfiap.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScaleSelector(
    selectedValue: Int?,
    onSelect: (Int) -> Unit,
    scaleRange: IntRange = 1..5
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        scaleRange.forEach { value ->
            OutlinedButton(
                onClick = { onSelect(value) },
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (selectedValue == value)
                        MaterialTheme.colors.primary.copy(alpha = 0.2f)
                    else
                        MaterialTheme.colors.surface
                ),
                border = if (selectedValue == value) ButtonDefaults.outlinedBorder else null
            ) {
                Text(value.toString())
            }
        }
    }
}
