package model;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

/**
 *  LibraryCard class
 *  records information on a user, the books borrowed by the user
 *      and the date the book was borrowed
 */
public class LibraryCard {
    private User user;
    private Map<String, LocalDate> borrowedBooks;

    public LibraryCard(User user) {
        this.user = user;
        this.borrowedBooks = new HashMap<>();
    }

    // getters for instance variables
    public User getUser() {
        return user;
    }

    public Map<String, LocalDate> getBorrowedBooks() {
        return borrowedBooks;
    }
}
