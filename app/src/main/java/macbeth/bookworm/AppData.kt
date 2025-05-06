package macbeth.bookworm

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Constants for new or invalid book ID's
const val BOOK_ID_NEW = -1
const val BOOK_ID_INVALID = -2

// Constants for Shred Preferences
const val SP_DATA = "DATA"
const val SP_DATA_BOOKS = "BOOKS"
const val SP_DATA_BOOKS_EMPTY = "[]"
const val SP_DATA_FILTER = "FILTER"
const val SP_DATA_FILTER_EMPTY = "BOOK_FILTER_ALL"
const val SP_DATA_COLOR = "COLOR"
const val SP_DATA_COLOR_EMPTY = "COLOR_SCHEME_SUNNY"

// Structure of a book
@Serializable
data class Book(var picture : String = "", var title : String = "", var author : String = "",
                var year : String = "", var edition : String = "", var pages : Int = 0,
                var pagesRead : Int = 0)

// Valid types of book filters
enum class BookFilter {
    BOOK_FILTER_ALL,
    BOOK_FILTER_IN_PROGRESS,
    BOOK_FILTER_COMPLETED,
}

// Valid type of colors
enum class ColorScheme {
    COLOR_SCHEME_SUNNY,
    COLOR_SCHEME_RAINY,
}

// Read book list from shared preferences
fun getBookList(context : Context) : List<Book> {
    val sp = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
    val json = sp.getString(SP_DATA_BOOKS, null) ?: SP_DATA_BOOKS_EMPTY
    val books = Json.decodeFromString<MutableList<Book>>(json)
    return books
}

// Write book list to shared preferences
fun setBookList(context : Context, books : List<Book>) {
    val sp = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
    val json = Json.encodeToString(books)
    sp.edit { putString(SP_DATA_BOOKS, json) }
}

// Get a specific book (or a new book) from the book list
fun getBook(books : List<Book>, bookId : Int) : Book? {
    var book = if (bookId == BOOK_ID_NEW) {
        Book()
    } else if (bookId >= 0 && bookId < books.size) {
        books[bookId]
    } else if (bookId >= books.size) {
        Log.e("BookWorm", "Out of Range book id $bookId >= ${books.size}")
        return null
    } else {
        Log.e("BookWorm", "Invalid book id $bookId")
        return null
    }
    return book
}

// Check if book is completed
fun bookCompleted(book : Book) : Boolean {
    return book.pages == book.pagesRead
}

fun getFilter(context: Context) : BookFilter {
    val sp = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
    val filter = BookFilter.valueOf(sp.getString(SP_DATA_FILTER, null) ?: SP_DATA_FILTER_EMPTY)
    return filter
}

fun setFilter(context: Context, filter : BookFilter) {
    val sp = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
    sp.edit { putString(SP_DATA_FILTER, filter.name) }
}

fun getColor(context: Context) : ColorScheme {
    val sp = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
    val color = ColorScheme.valueOf(sp.getString(SP_DATA_COLOR, null) ?: SP_DATA_COLOR_EMPTY)
    return color
}

fun setColor(context: Context, color : ColorScheme) {
    val sp = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
    sp.edit { putString(SP_DATA_COLOR, color.name) }
}

