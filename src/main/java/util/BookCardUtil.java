package util;

import model.BookCard;

import java.util.HashMap;
import java.util.Map;

public class BookCardUtil {
    public Map<String, BookCard> bookCardCatalog = new HashMap<>();

    public void addBook(String ISBN, String bookName, String authorName) {
        BookCard book;
        if (bookCardCatalog.containsKey(bookName.toLowerCase())) {
            book = bookCardCatalog.get(bookName.toLowerCase());
            addMoreCopy(book);
        } else {
            book = new BookCard(ISBN, bookName, authorName);
            bookCardCatalog.put(bookName.toLowerCase(), book);
        }

    }

    public boolean containsKey(String bookName) {
        bookName = bookName.toLowerCase();
        return bookCardCatalog.containsKey(bookName);
    }

    public int getTotalNoOfCopies(String bookName) {
        bookName = bookName.toLowerCase();
        return bookCardCatalog.get(bookName).getTotalNoOfCopies();
    }

    public void addMoreCopy(BookCard bookCard) {
        bookCard.setTotalNoOfCopies(bookCard.getTotalNoOfCopies() + 1);
    }

    public boolean isOnShelf(String bookName) {
        bookName = bookName.toLowerCase();
        BookCard bookCard = bookCardCatalog.get(bookName);
        return bookCard.getCopiesBorrowed() < bookCard.getTotalNoOfCopies();
    }

    public void returnBookToShelf(String bookName) {
        bookName = bookName.toLowerCase();
        BookCard bookCard = bookCardCatalog.get(bookName);
        bookCard.setCopiesBorrowed(bookCard.getCopiesBorrowed() - 1);
    }

    public void collectBookFromShelf(String bookName) {
        bookName = bookName.toLowerCase();
        BookCard bookCard = bookCardCatalog.get(bookName);
        bookCard.setCopiesBorrowed(bookCard.getCopiesBorrowed() + 1);
    }
}
