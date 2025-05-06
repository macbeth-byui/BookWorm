package macbeth.bookworm

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ColorIcon(color : ColorScheme, onChanged : (ColorScheme) -> Unit) {
    var colorMenuVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    IconButton(
        onClick = {
            colorMenuVisible = !colorMenuVisible
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.palette_24px),
            contentDescription = "Color Menu",
            tint = Color.White,
        )
    }
    DropdownMenu(
        expanded = colorMenuVisible,
        onDismissRequest = { colorMenuVisible = false },
        shape = RoundedCornerShape(16.dp),
    ) {
        DropdownMenuItem(
            text = { Text("Sunny") },
            leadingIcon = {
                if (color == ColorScheme.COLOR_SCHEME_SUNNY) {
                    Icon(
                        painter = painterResource(R.drawable.check_24px),
                        contentDescription = "Sunny",
                        tint = Color.Black,
                    )
                }
            },
            onClick = {
                colorMenuVisible = false
                setColor(context, ColorScheme.COLOR_SCHEME_SUNNY)
                onChanged(ColorScheme.COLOR_SCHEME_SUNNY)
            }
        )
        DropdownMenuItem(
            text = { Text("Rainy") },
            leadingIcon = {
                if (color == ColorScheme.COLOR_SCHEME_RAINY) {
                    Icon(
                        painter = painterResource(R.drawable.check_24px),
                        contentDescription = "Rainy",
                        tint = Color.Black,
                    )
                }
            },
            onClick = {
                colorMenuVisible = false
                setColor(context, ColorScheme.COLOR_SCHEME_RAINY)
                onChanged(ColorScheme.COLOR_SCHEME_RAINY)
            }
        )
    }
}