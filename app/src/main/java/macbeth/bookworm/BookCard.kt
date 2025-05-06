package macbeth.bookworm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Display a single Card for a book
@Composable
fun BookCard(index : Int, book : Book, nav: NavController) {
    // The card and the subsequent Row and Status Bar will be created serially
    // like they were in a column.
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        onClick = {
            nav.navigate("$NAV_EDIT_BOOK/$index")
        },
    ) {
        // The row contains image and a column of book information
        Row {
            DisplayBookImage(book.picture)

            Column (
                modifier = Modifier.padding(0.dp, 8.dp).fillMaxSize(),
            ) {
                Row {
                    Text(
                        book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }
                Row {
                    Text(
                        book.author,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }
                val spacer = if (book.edition.isEmpty()) {
                    ""
                } else if (book.year.isEmpty()) {
                    ""
                } else {
                    " - "
                }
                Row {
                    Text(
                        "${book.edition}$spacer${book.year}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }
            }
        }

        // Status Bar goes beneath the row above
        BookStatusBar(book.pagesRead,book.pages)
    }
}
