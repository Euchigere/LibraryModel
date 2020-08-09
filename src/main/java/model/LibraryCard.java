package model;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class LibraryCard {
    private PersonBio person;
    Map<String, LocalDate> borrowedBooks;

    public LibraryCard(PersonBio person) {
        this.person = person;
        this.borrowedBooks = new HashMap<>();
    }
}
