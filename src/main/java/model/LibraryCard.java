package model;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class LibraryCard {
    private User person;
    private Map<String, LocalDate> borrowedBooks;

    public LibraryCard(User person) {
        this.person = person;
        this.borrowedBooks = new HashMap<>();
    }

    public User getPerson() {
        return person;
    }

    public Map<String, LocalDate> getBorrowedBooks() {
        return borrowedBooks;
    }
}
