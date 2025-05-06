package macbeth.bookworm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import macbeth.bookworm.ColorScheme

private val RainyScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = Gray,
    tertiary = LightBlue,
    onPrimary = LightGray,
    onSecondary = LightGray,
    onTertiary = LightGray,
)

private val SunnyScheme = lightColorScheme(
    primary = Orange,
    secondary = LightOrange,
    tertiary = LightYellow,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
)

@Composable
fun BookWormTheme(
    color : ColorScheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when(color) {
        ColorScheme.COLOR_SCHEME_SUNNY -> SunnyScheme
        ColorScheme.COLOR_SCHEME_RAINY -> RainyScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}