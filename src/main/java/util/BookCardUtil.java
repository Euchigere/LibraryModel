package util;

import model.BookCard;

import java.util.HashMap;
import java.util.Map;

/**
 * BookCard Utility class
 * Contains methods to manipulate the BookCard catalog
 * BookCard catalog contains record of all the bookcards created by the class
 */

public class BookCardUtil {
    // bookCardCatalog contains records of all the book cards
    public Map<String, BookCard> bookCardCatalog = new HashMap<>();

    // check if the book is in the catalog
    public boolean containsBook(String bookName) {
        bookName = bookName.toLowerCase();
        return bookCardCatalog.containsKey(bookName);
    }

    // Method to create a new BookCard for each book in the library
    public void addBook(String ISBN, String bookName, String authorName) {
        BookCard book;
        if (containsBook(bookName.toLowerCase())) {
            book = bookCardCatalog.get(bookName.toLowerCase());
            addMoreCopy(book);
        } else {
            book = new BookCard(ISBN, bookName, authorName);
            bookCardCatalog.put(bookName.toLowerCase(), book);
        }

    }

    // Checks and return the number of copies of a particular book
    // or returns -1 if the book is not in the catalog
    public int getTotalNoOfCopies(String bookName) {
        bookName = bookName.toLowerCase();
        // confirms that book is in catalogue else return -1
        if (!containsBook(bookName)) {
            return -1;
        }
        return bookCardCatalog.get(bookName).getTotalNoOfCopies();
    }

    // Increments the number of copies of a book in the book card
    public void addMoreCopy(BookCard bookCard) {
        bookCard.setTotalNoOfCopies(bookCard.getTotalNoOfCopies() + 1);
    }

    // checks if a book is available for borrow
    public boolean isOnShelf(String bookName) {
        bookName = bookName.toLowerCase();
        // confirms that book is in catalogue else return
        if (!containsBook(bookName)) {
            return false;
        }
        BookCard bookCard = bookCardCatalog.get(bookName);
        return bookCard.getCopiesBorrowed() < bookCard.getTotalNoOfCopies();
    }

    // Increases the number of books available in a book card accordingly
    public void returnBookToShelf(String bookName) {
        bookName = bookName.toLowerCase();
        // confirms that book is in catalogue else return
        if (!containsBook(bookName)) {
            return;
        }
        BookCard bookCard = bookCardCatalog.get(bookName);
        bookCard.setCopiesBorrowed(bookCard.getCopiesBorrowed() - 1);
    }

    // Increases the number of borrowed copy of a book in it's bookCard
    public void collectBookFromShelf(String bookName) {
        bookName = bookName.toLowerCase();
        // confirms that book is in catalogue else return
        if (!containsBook(bookName)) {
            return;
        }
        BookCard bookCard = bookCardCatalog.get(bookName);
        bookCard.setCopiesBorrowed(bookCard.getCopiesBorrowed() + 1);
    }
}
