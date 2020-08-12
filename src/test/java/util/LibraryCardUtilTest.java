package util;

import model.Grade;
import model.LibraryCard;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryCardUtilTest {
    LibraryCardUtil libraryCardUtil;
    @BeforeEach
    void setUp() {
        libraryCardUtil = new LibraryCardUtil();
    }

    @Test
    void testAddBorrowedBook() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        libraryCardUtil.addBorrowedBook(student, "Purple Hibiscus");
        libraryCardUtil.addBorrowedBook(student, "Chike and the river");
        LibraryCard libraryCard = libraryCardUtil.libraryCardCatalog.get(student.getName());

        assertAll(
                () -> assertTrue(libraryCardUtil.libraryCardCatalog.containsKey(student.getName())),
                () -> assertEquals(2, libraryCard.getBorrowedBooks().size()),
                () -> assertTrue(libraryCard.getBorrowedBooks().containsKey("purple hibiscus")),
                () -> assertTrue(libraryCard.getBorrowedBooks().containsKey("chike and the river"))
        );
    }

    @Test
    void testThatAddBorrowedBookAddsOnlyUniqueValue() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        libraryCardUtil.addBorrowedBook(student, "Purple Hibiscus");
        libraryCardUtil.addBorrowedBook(student, "Purple Hibiscus");
        LibraryCard libraryCard = libraryCardUtil.libraryCardCatalog.get(student.getName());

        assertAll(
                () -> assertTrue(libraryCardUtil.libraryCardCatalog.containsKey(student.getName())),
                () -> assertEquals(1, libraryCard.getBorrowedBooks().size(), "There should only be one book"),
                () -> assertTrue(libraryCard.getBorrowedBooks().containsKey("purple hibiscus"), "Library card should contain book added")
        );
    }


    @Test
    void testReturnBook() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        libraryCardUtil.addBorrowedBook(student, "Purple Hibiscus");
        libraryCardUtil.returnBook(student, "Purple Hibiscus");
        LibraryCard libraryCard = libraryCardUtil.libraryCardCatalog.get(student.getName());

        assertAll(
                () -> assertTrue(libraryCardUtil.libraryCardCatalog.containsKey(student.getName())),
                () -> assertEquals(0, libraryCard.getBorrowedBooks().size(), "There should only be no book"),
                () -> assertFalse(libraryCard.getBorrowedBooks().containsKey("purple hibiscus"), "Library card should remove book added")
        );

    }

    @Test
    void testContainsKeyMethod() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        libraryCardUtil.addBorrowedBook(student, "Purple Hibiscus");

        assertAll(
                () -> assertTrue(libraryCardUtil.containsKey(student.getName()))

        );
    }
}