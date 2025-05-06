package macbeth.bookworm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Display a status bar based on current and total pages
@Composable
fun BookStatusBar(current : Int, total : Int) {
    // Status Bar is a Box, and then a Box on top based on the % complete,
    // and then text showing pages.  All 3 are superimposed on each other.  Box's
    // are meant for stacking.
    Box(
        modifier = Modifier.fillMaxSize().height(20.dp).background(MaterialTheme.colorScheme.tertiary),
    ) {
        // The red box and the text are inside the outer black box
        val percent = current.toFloat() / total.toFloat()
        Box(
            modifier = Modifier.fillMaxWidth(percent).fillMaxHeight().background(MaterialTheme.colorScheme.primary),
        ) {
        }
        Text(
            "$current / $total",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
