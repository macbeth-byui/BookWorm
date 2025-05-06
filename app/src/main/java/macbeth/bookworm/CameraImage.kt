package macbeth.bookworm

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File
import java.util.UUID

// Reusable component to display a book image
@Composable
fun DisplayBookImage(picture : String) {
    if (picture.isEmpty()) {
        // Show a default picture
        Image(
            painter = painterResource(R.drawable.bookworm),
            contentDescription = "No Book Picture",
            modifier = Modifier.padding(20.dp).size(125.dp, 125.dp)
        )
    } else {
        // Coil library is used to display picture
        val file = File(LocalContext.current.filesDir, picture)
        AsyncImage (
            model = file,
            contentDescription = "Book Picture",
            modifier = Modifier.padding(8.dp).size(150.dp, 150.dp)
        )
    }
}

// Create an image file on the phone
fun createImageFile(context : Context) : File {
    val uuid = UUID.randomUUID().toString()
    val file = File.createTempFile(
        "photo_$uuid", ".jpg",
        context.filesDir
    )
    return file
}

// Delete an image file from the phone
fun deleteImageFile(context : Context, fileName : String) {
    if (fileName.isEmpty()) {
        return
    }
    val file = File(context.filesDir, fileName)
    file.delete()
}

// Delete a list of image files from the phone
fun deleteMultipleImageFiles(context : Context, fileNames : List<String>) {
    fileNames.forEach { deleteImageFile(context, it) }
}

// Get the URI for a picture file on the phone
fun getUriFromFile(context : Context, file : File) : Uri {
    val uri = FileProvider.getUriForFile(context,
        "${context.packageName}.files", file)
    return uri
}