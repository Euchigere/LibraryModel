package util;

import model.LibraryCard;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 * LibraryCardUtil contains controller methods for the library model
 * It is composed of LibraryCaryUtil, BookCardUtil and MyPriorityQueue custom classes
 */

public class LibraryCardUtil {
    public Map<String, LibraryCard> libraryCardCatalog = new HashMap<>();

    public void addBorrowedBook(User user, String bookName) {
        bookName = bookName.toLowerCase();
        LibraryCard libraryCard;

        if (!libraryCardCatalog.containsKey(user.getName())) {
            libraryCard = new LibraryCard(user);
            libraryCardCatalog.put(user.getName(), libraryCard);
        }

        libraryCard = libraryCardCatalog.get(user.getName());

        if (libraryCard.getBorrowedBooks().containsKey(bookName)
                || libraryCard.getBorrowedBooks().size() == 3) {
            return;
        }
        libraryCard
                .getBorrowedBooks()
                .put(bookName, LocalDate.now());
    }

    public void returnBook(User user, String bookName) {
        if (!libraryCardCatalog.containsKey(user.getName())) {
            return;
        }
        bookName = bookName.toLowerCase();

        libraryCardCatalog
                .get(user.getName())
                .getBorrowedBooks()
                .remove(bookName);
    }

    public boolean containsKey(String userName) {
        return libraryCardCatalog.containsKey(userName);
    }
}
