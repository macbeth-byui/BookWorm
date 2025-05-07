package macbeth.bookworm

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight

// Displays a single book and allows the user to modify (or delete) the contents of that book.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(nav : NavController, bookId : Int) {
    val context = LocalContext.current

    // Only read the books once
    val books by remember { mutableStateOf(getBookList(context)) }

    val book = getBook(books, bookId)?: run {
        nav.navigate(NAV_LIST_BOOKS)
        return
    }

    // Remember fields in the form
    var pagesRead by remember { mutableIntStateOf(book.pagesRead) }
    var title by remember { mutableStateOf(book.title) }
    var author by remember { mutableStateOf(book.author) }
    var edition by remember { mutableStateOf(book.edition) }
    var year by remember { mutableStateOf(book.year) }
    var pages by remember { mutableIntStateOf(book.pages) }
    var picture by remember { mutableStateOf(book.picture) }
    var newPicture by remember { mutableStateOf("") }
    var oldPictures = remember { mutableStateListOf<String>() }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        // Respond the success or failure of the camera result
        if (success) {
            if (!picture.isEmpty()) {
                oldPictures.add(picture)
            }
            picture = newPicture
        } else {
            deleteImageFile(context, newPicture)
        }
    }
    val topBarTitle = if (bookId == BOOK_ID_NEW) {
        "New Book"
    } else {
        "Update Book"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        topBarTitle,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        },
    ) { surfacePadding ->
        // Surface takes a default modifier, applies the padding
        // in all four directions around any app boars on top or
        // bottom from the Scaffold, and the fills the screen.
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(surfacePadding).fillMaxSize(),
        ) {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                // Form Fields
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image is put on a surface to provide on-click
                    // Fill the surface with the picture sized at 150x150
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(16.dp).size(150.dp, 150.dp),
                        onClick = {
                            val file = createImageFile(context)
                            newPicture = file.name
                            val uri = getUriFromFile(context, file)
                            Log.d("BookWorm", "Taking picture: $newPicture")
                            // Launch camera intent
                            takePictureLauncher.launch(uri)
                        }
                    ) {
                        DisplayBookImage(picture)
                    }

                    TextField(
                        value = if (pagesRead == 0) { "" } else { pagesRead.toString() },
                        onValueChange = { newValue ->
                            pagesRead = if (newValue.isEmpty()) {
                                0
                            } else {
                                newValue.toIntOrNull() ?: pagesRead
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Pages Read") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            focusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                        ),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                    )
                }

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Title") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                        unfocusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(32.dp)
                )

                TextField(
                    value = author,
                    onValueChange = { author = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Author") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                        unfocusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp)
                )

                TextField(
                    value = edition,
                    onValueChange = { edition = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Edition") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                        unfocusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp)
                )

                TextField(
                    value = year,
                    onValueChange = { year = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Year") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                        unfocusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp)
                )

                TextField(
                    value = if (pages == 0) { "" } else { pages.toString() },
                    onValueChange = { newValue ->
                        pages = if (newValue.isEmpty()) {
                            0
                        } else {
                            newValue.toIntOrNull() ?: pages
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Pages") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                        unfocusedLabelColor =  MaterialTheme.colorScheme.onTertiary,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp)
                )

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        onClick = {
                            deleteMultipleImageFiles(context, oldPictures)
                            book.title = title
                            book.author = author
                            book.edition = edition
                            book.year = year
                            book.pages = pages
                            book.pagesRead = pagesRead
                            book.picture = picture
                            var newBooks = books.toMutableList()
                            if (bookId == BOOK_ID_NEW) {
                                newBooks.add(book)
                            } else {
                                newBooks[bookId] = book
                            }
                            setBookList(context, newBooks)
                            nav.navigate(NAV_LIST_BOOKS)
                        },
                    ) {
                        Text(
                            "Save",
                            fontSize = 24.sp,
                        )
                    }
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        onClick = {
                            deleteMultipleImageFiles(context, oldPictures)
                            deleteImageFile(context, picture)
                            var newBooks = books.toMutableList()
                            if (bookId != BOOK_ID_NEW) {
                                newBooks.removeAt(bookId)
                                setBookList(context, newBooks)
                            }
                            nav.navigate(NAV_LIST_BOOKS) },
                    ) {
                        Text(
                            "Delete",
                            fontSize = 24.sp,
                        )
                    }
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        onClick = {
                            deleteMultipleImageFiles(context, oldPictures.slice(1..(oldPictures.size-1)))
                            deleteImageFile(context, newPicture)
                            nav.navigate(NAV_LIST_BOOKS)
                        },
                    ) {
                        Text(
                            "Cancel",
                            fontSize = 24.sp,
                        )
                    }
                }
            }
        }
    }

}
