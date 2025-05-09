package macbeth.bookworm

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.common.util.concurrent.MoreExecutors
import macbeth.bookworm.ui.theme.BookWormTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel : MainViewModel
    private var mediaController : MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = MainViewModel(getColor(this))
        setContent {
            val color = viewModel.color.value
            BookWormTheme(color = color) {
                AppControl(viewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()
                // Put the player in the viewModel which will trigger the UI to redraw
                viewModel.player.value = mediaController
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onDestroy() {
        mediaController?.release()
        val intent = Intent(this, PlaybackService::class.java)
        stopService(intent)
        super.onDestroy()
    }
}

// Store data that can be communicated with the UI
class MainViewModel(requestedColor : ColorScheme) : ViewModel() {
    var player = mutableStateOf<Player?>(null)
    var color = mutableStateOf(requestedColor)
    val tempPictures = mutableStateListOf<String>()
}

