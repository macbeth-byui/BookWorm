package macbeth.bookworm

import androidx.annotation.OptIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState

@OptIn(UnstableApi::class)
@Composable
fun MusicIcon(player : Player?) {
    // If the player is initialized, then display the play button
    player?.let {
        val state = rememberPlayPauseButtonState(it)
        val icon =
            if (state.showPlay) R.drawable.play_arrow_24px else R.drawable.pause_24px
        IconButton(
            onClick = state::onClick,
            enabled = state.isEnabled,
        ) {
            Icon(
                painter = painterResource(id = icon),
                "Music Control",
                tint = Color.White,
            )
        }
    }
}