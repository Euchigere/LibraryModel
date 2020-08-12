package util;

import model.BookCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookCardUtilTest {
    BookCardUtil bookCardUtil;
    @BeforeEach
    void setUp() {
        bookCardUtil = new BookCardUtil();
    }

    @Test
    @DisplayName("Book Is Added Successfully")
    void addBookTest() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");
        bookCardUtil.addBook("002", "Things Fall Apart", "Chinua Achebe");

        assertAll(
                () -> assertEquals(2, bookCardUtil.bookCardCatalog.size()),
                () -> assertTrue(bookCardUtil.bookCardCatalog.containsKey("purple hibiscus")),
                () -> assertTrue(bookCardUtil.bookCardCatalog.containsKey("things fall apart"))
        );
    }

    @Test
    @DisplayName("Check unique key value when adding another copy of a particular Book")
    void addBookUniqueKeyTest() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");

        assertAll(
                () -> assertEquals(1, bookCardUtil.bookCardCatalog.size()),
                () -> assertTrue(bookCardUtil.bookCardCatalog.containsKey("purple hibiscus"))
        );
    }

    @Test
    void containsKeyTest() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");
        assertAll(
                () -> assertTrue(bookCardUtil.containsKey("purple hibiscus")),
                () -> assertFalse(bookCardUtil.containsKey("things fall apart"))
        );
    }

    @Test
    void getTotalNoOfCopies() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");

        assertEquals(1, bookCardUtil.getTotalNoOfCopies("Purple Hibiscus"));
    }

    @Test
    void addMoreCopy() {
        BookCard bookCard = new BookCard("001", "Purple Hibiscus", "Chimamanda Adichie");
        bookCardUtil.addMoreCopy(bookCard);

        assertEquals(2, bookCard.getTotalNoOfCopies());
    }

    @Test
    void isOnShelf() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");
        assertTrue(bookCardUtil.isOnShelf("Purple Hibiscus"));
    }

    @Test
    void returnBookToShelf() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");
        bookCardUtil.collectBookFromShelf("Purple Hibiscus");
        bookCardUtil.returnBookToShelf("Purple Hibiscus");
        assertEquals(0, bookCardUtil.bookCardCatalog.get("purple hibiscus").getCopiesBorrowed());

    }

    @Test
    void collectBookFromShelf() {
        bookCardUtil.addBook("001", "Purple Hibiscus", "Chimamanda Adichie");
        bookCardUtil.collectBookFromShelf("Purple Hibiscus");
        assertEquals(1, bookCardUtil.bookCardCatalog.get("purple hibiscus").getCopiesBorrowed());
    }
}