package macbeth.bookworm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// This contains the design and logic for the list of books.  Includes the
// loading of the books, creation of the LazyColumn of BookCard's, and the
// floating button to add new books, menu bar, and stats bar.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(nav : NavController, viewModel: MainViewModel) {
    val context = LocalContext.current

    var filter by remember { mutableStateOf(getFilter(context)) }

    // Only read the books once
    val books by remember { mutableStateOf<List<Book>>(getBookList(context)) }

    Scaffold(
        // Menu on Title Bar
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bookworm),
                            contentDescription = "Book Worm",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            "Book Worm",
                            fontWeight = FontWeight.Bold,

                        )
                    }
                },
                actions = {
                    MusicIcon(viewModel.player)
                    FilterIcon(filter) { filter = it }
                    ColorIcon(viewModel.color) { viewModel.color = it }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                BookStats(filter, books)
            }
        },
        // The floating action button is the means the user creates a new book
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    nav.navigate(NAV_ADD_BOOK)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = "Add",
                    tint = Color.White,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { surfacePadding ->
        // Surface takes a default modifier, applies the padding
        // in all four directions around any app boars on top or
        // bottom from the Scaffold, and the fills the screen.
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(surfacePadding).fillMaxSize()
        ) {
            // Create a lazy column which will load rows that are viewable
            LazyColumn(
                modifier = Modifier.padding(16.dp).fillMaxSize(),
            ) {
                itemsIndexed(books) { index, book ->
                    if (filter == BookFilter.BOOK_FILTER_ALL ||
                        (filter == BookFilter.BOOK_FILTER_COMPLETED && bookCompleted(book)) ||
                        (filter == BookFilter.BOOK_FILTER_IN_PROGRESS && !bookCompleted(book))) {
                        BookCard(index, book, nav)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
