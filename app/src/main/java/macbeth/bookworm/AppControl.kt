package macbeth.bookworm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

const val NAV_LIST_BOOKS = "NAV_LIST_BOOKS"
const val NAV_ADD_BOOK = "NAV_ADD_BOOK"
const val NAV_EDIT_BOOK = "NAV_EDIT_BOOK"
const val NAV_PARAM_BOOK_ID = "BOOK_ID"

// Check Permissions, organize navigation, and then launch App
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppControl(viewModel: MainViewModel) {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (!cameraPermissionState.status.isGranted) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = { cameraPermissionState.launchPermissionRequest() },
                modifier = Modifier.align(Alignment.Center),
            ) {
                Text(
                    "Camera Permission Requested",
                    fontSize = 24.sp,
                )
            }
        }
    } else {
        val nav = rememberNavController()
        NavHost(navController = nav, startDestination = NAV_LIST_BOOKS) {
            composable(NAV_LIST_BOOKS) {
                BookListScreen(nav, viewModel)
            }
            composable(
                "$NAV_EDIT_BOOK/{$NAV_PARAM_BOOK_ID}",
                arguments = listOf(
                    navArgument(NAV_PARAM_BOOK_ID) { type = NavType.IntType }
                )) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getInt(NAV_PARAM_BOOK_ID) ?: BOOK_ID_INVALID
                BookDetailScreen(nav, bookId)
            }
            composable(NAV_ADD_BOOK) {
                BookDetailScreen(nav, BOOK_ID_NEW)
            }
        }
    }
}
