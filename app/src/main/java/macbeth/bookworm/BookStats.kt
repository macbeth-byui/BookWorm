package macbeth.bookworm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

// Display the filter and the stats for that filter
@Composable
fun BookStats(filterChoice : BookFilter, books : List<Book>) {
    var count = 0
    var pages = 0
    var pagesRead = 0
    for (book in books) {
        if (filterChoice == BookFilter.BOOK_FILTER_ALL ||
            (filterChoice == BookFilter.BOOK_FILTER_COMPLETED && bookCompleted(book)) ||
            (filterChoice == BookFilter.BOOK_FILTER_IN_PROGRESS && !bookCompleted(book))
        ) {
            count++
            pages += book.pages
            pagesRead += book.pagesRead
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        val filterText = if (filterChoice == BookFilter.BOOK_FILTER_ALL) {
            "All Books"
        } else if (filterChoice == BookFilter.BOOK_FILTER_IN_PROGRESS) {
            "In Progress Books"
        } else {
            "Completed Books"
        }
        Text(
            filterText,
            fontWeight = FontWeight.Bold,
        )
        HorizontalDivider()
        val countText = if (count == 1) {
            "1 Book"
        } else {
            "$count Books"
        }
        val pagesText = if (pages == 1) {
            "1 Total Page"
        } else {
            "$pages Total Pages"
        }
        val pagesReadText = if (pagesRead == 1) {
            "1 Page Read"
        } else {
            "$pagesRead Pages Read"
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = countText,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Text(
                text = pagesReadText,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Text(
                text = pagesText,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}