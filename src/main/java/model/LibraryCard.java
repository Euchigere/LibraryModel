package model;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class LibraryCard {
    private User person;
    Map<String, LocalDate> borrowedBooks;

    public LibraryCard(User person) {
        this.person = person;
        this.borrowedBooks = new HashMap<>();
    }
}
