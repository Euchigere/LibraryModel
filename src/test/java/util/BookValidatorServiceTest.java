package util;

import model.Grade;
import model.Student;
import org.junit.jupiter.api.Test;
import static util.BookValidatorService.*;
import static util.BookValidatorService.BookValidationResult.*;
import static util.MOCK_DATABASE.bookCardCatalog;

import static org.junit.jupiter.api.Assertions.*;

class BookValidatorServiceTest {

    @Test
    void testIsValidBookName() {
        assertAll(
                () -> assertEquals(INVALID_BOOK_NAME, isValidBookName().apply("")),
                () -> assertEquals(INVALID_BOOK_NAME, isValidBookName().apply(null)),
                () -> assertEquals(INVALID_BOOK_NAME, isValidBookName().apply("   "))
        );
    }

    @Test
    void testLibraryHasBook() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");

        assertAll(
                () -> assertEquals(LIBRARY_DOES_NOT_HAVE_BOOK, libraryHasBook().apply("There was a country")),
                () -> assertEquals(BOOK_IS_AVAILABLE, libraryHasBook().apply("Purple Hibiscus")),
                () -> assertEquals(BOOK_IS_AVAILABLE, libraryHasBook().apply("Things Fall Apart"))
        );
        bookCardCatalog.clear();
    }

    @Test
    void testBookIsOnShelf() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");

        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);

        LibraryUtil.borrowBook(student, "Things Fall Apart");

        assertAll(
                () -> assertEquals(BOOK_IS_AVAILABLE, isBookOnShelf().apply("Purple Hibiscus")),
                () -> assertEquals(BOOK_IS_NOT_ON_SHELF, isBookOnShelf().apply("Things Fall Apart"))
        );
        bookCardCatalog.clear();

    }

    @Test
    void testCombinatorPattern() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");

        BookValidationResult bookResult1 = isValidBookName().and(isBookOnShelf()).apply("  ");
        BookValidationResult bookResult2 = isValidBookName().and(isBookOnShelf()).and(libraryHasBook()).apply("Purple Hibiscus");
        BookValidationResult bookResult3 = libraryHasBook().and(isBookOnShelf()).apply("Purple Hibiscus");

        assertAll(
                () -> assertEquals(INVALID_BOOK_NAME, bookResult1),
                () -> assertEquals(BOOK_IS_AVAILABLE, bookResult2),
                () ->assertEquals(BOOK_IS_AVAILABLE, bookResult3)
        );
        bookCardCatalog.clear();
    }
}