package util;

import model.*;
import java.util.HashMap;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUtilTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");
        LibraryUtil.addBookToLibrary("003", "There was a Country", "Chinua Achebe");
        LibraryUtil.addBookToLibrary("004", "How Will You Measure Your Life?",
                "Clayton M. Christensen");
        LibraryUtil.addBookToLibrary("005", "Chike and the River", "Chinua Achebe");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        LibraryUtil.bookCatalog = new HashMap<>();
    }


    @org.junit.jupiter.api.Nested
    class addBookToLibraryTest {
        @org.junit.jupiter.api.Test
        void addNewBookToBookCatalog() {
            assertAll(
                    () -> assertTrue(LibraryUtil.bookCatalog.containsKey("Purple Hibiscus")),
                    () -> assertTrue(LibraryUtil.bookCatalog.containsKey("Things Fall Apart")),
                    () -> assertTrue(LibraryUtil.bookCatalog.containsKey("There was a Country")),
                    () -> assertEquals(3, LibraryUtil.bookCatalog.size())
            );
        }

        @org.junit.jupiter.api.Test
        void addBooksAlreadyInBookCatalog() {
            LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
            LibraryUtil.addBookToLibrary("003", "There was a Country", "Chinua Achebe");

            assertAll(
                    () -> assertTrue(LibraryUtil.bookCatalog.containsKey("Purple Hibiscus")),
                    () -> assertTrue(LibraryUtil.bookCatalog.containsKey("Things Fall Apart")),
                    () -> assertTrue(LibraryUtil.bookCatalog.containsKey("There was a Country")),
                    () -> assertEquals(3, LibraryUtil.bookCatalog.size()),
                    () -> assertEquals(2, LibraryUtil.bookCatalog.get("Purple Hibiscus").getTotalNoOfCopies()),
                    () -> assertEquals(2, LibraryUtil.bookCatalog.get("There was a Country").getTotalNoOfCopies()));

        }
    }


    @org.junit.jupiter.api.Test
    void borrowBook() {
        Student student1 = new Student("Solomon", "male", "1992-09-21",
                "07068660641", "001", Grade.CLASS_1);
        Student student2 = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Student student3 = new Student("Johnbosco", "male", "1992-09-21",
                "07068660641", "003", Grade.CLASS_5);
        Staff teacher1 = new Staff("Emmanuella", "f", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);
        Staff teacher2 = new Staff("Daro", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);
        Staff teacher3 = new Staff("David", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.borrowBook(student1, "Purple Hibiscus");
        LibraryUtil.borrowBook(student3, "Purple Hibiscus");
        LibraryUtil.borrowBook(student2, "Things fall apart");
        LibraryUtil.borrowBook(teacher1, "Things fall apart");
        LibraryUtil.borrowBook(student2, "Purple Hibiscus");
        LibraryUtil.borrowBook(teacher2, "Purple Hibiscus");


        int i = 1;
        while (!LibraryUtil.waitingList.isEmpty()) {
            System.out.print(i + ": " + LibraryUtil.waitingList.peek().getKey().getName());
            System.out.println(" : " + LibraryUtil.waitingList.remove().getValue());
            i++;
        }
        LibraryUtil.waitingList = new MyPriorityQueue();
    }

    @org.junit.jupiter.api.Test
    void processWaitingList() {
    }
}