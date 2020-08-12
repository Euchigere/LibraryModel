package util;

import model.LibraryCard;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 * LibraryCardUtil class
 * Contains methods to modify and access the libraryCard catalog
 * The library card catalog contains records of all the library cards in the library
 */

public class LibraryCardUtil {
    // record of all the library cards in the library
    public Map<String, LibraryCard> libraryCardCatalog = new HashMap<>();

    // checks if user is in catalog
    public boolean hasUser(String userName) {
        return libraryCardCatalog.containsKey(userName);
    }

    // Instantiates new library card for new users and or update the information on book borrowed
    public void addToLibraryCard(User user, String bookName) {
        bookName = bookName.toLowerCase();
        LibraryCard libraryCard;

        // if new user, create new library card
        if (!hasUser(user.getName())) {
            libraryCard = new LibraryCard(user);
            libraryCardCatalog.put(user.getName(), libraryCard);
        }
        libraryCard = libraryCardCatalog.get(user.getName());


        if (!isEligible(user, bookName)) {
            return;
        }
        // stores record of book borrowed and date in users library card
        libraryCard
                .getBorrowedBooks()
                .put(bookName, LocalDate.now());
    }

    // removes the record of book user has returned from user's library card
    public void returnBook(User user, String bookName) {
        if (!hasUser(user.getName())) {
            return;
        }
        bookName = bookName.toLowerCase();

        libraryCardCatalog
                .get(user.getName())
                .getBorrowedBooks()
                .remove(bookName);
    }

    // checks if user is eligible to borrow book
    // That is that user doesn't borrow the same book more than once
    // and restricts user to borrow maximum of 3 books
    public boolean isEligible(User user, String bookName) {
        bookName = bookName.toLowerCase();
        if (!hasUser(user.getName())) {
            return true;
        } else if (libraryCardCatalog.get(user.getName()).getBorrowedBooks().containsKey(bookName)
                || libraryCardCatalog.get(user.getName()).getBorrowedBooks().size() == 3) {
            return false;
        }
        return true;
    }
}
