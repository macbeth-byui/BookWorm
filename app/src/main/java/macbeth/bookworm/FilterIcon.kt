package macbeth.bookworm

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FilterIcon(filter : BookFilter, onChange : (BookFilter) -> Unit) {
    var filterMenuVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    IconButton(
        onClick = {
            filterMenuVisible = !filterMenuVisible
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.filter_alt_24px),
            contentDescription = "Filter Menu",
            tint = Color.White,
        )
    }
    DropdownMenu(
        expanded = filterMenuVisible,
        onDismissRequest = { filterMenuVisible = false },
        shape = RoundedCornerShape(16.dp),
    ) {
        DropdownMenuItem(
            text = { Text("Show All") },
            leadingIcon = {
                if (filter == BookFilter.BOOK_FILTER_ALL) {
                    Icon(
                        painter = painterResource(R.drawable.check_24px),
                        contentDescription = "Add",
                        tint = Color.Black,
                    )
                }
            },
            onClick = {
                filterMenuVisible = false
                setFilter(context, BookFilter.BOOK_FILTER_ALL)
                onChange(BookFilter.BOOK_FILTER_ALL)
            }
        )
        DropdownMenuItem(
            text = { Text("Show In Progress") },
            leadingIcon = {
                if (filter == BookFilter.BOOK_FILTER_IN_PROGRESS) {
                    Icon(
                        painter = painterResource(R.drawable.check_24px),
                        contentDescription = "Add",
                        tint = Color.Black,
                    )
                }
            },
            onClick = {
                filterMenuVisible = false
                setFilter(context, BookFilter.BOOK_FILTER_IN_PROGRESS)
                onChange(BookFilter.BOOK_FILTER_IN_PROGRESS)
            }
        )
        DropdownMenuItem(
            text = { Text("Show Completed") },
            leadingIcon = {
                if (filter == BookFilter.BOOK_FILTER_COMPLETED) {
                    Icon(
                        painter = painterResource(R.drawable.check_24px),
                        contentDescription = "Add",
                        tint = Color.Black,
                    )
                }
            },
            onClick = {
                filterMenuVisible = false
                setFilter(context, BookFilter.BOOK_FILTER_COMPLETED)
                onChange(BookFilter.BOOK_FILTER_COMPLETED)
            }
        )
    }
}