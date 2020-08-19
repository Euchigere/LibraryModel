package util;

import model.*;

import java.util.AbstractMap;
import static util.MOCK_DATABASE.*;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUtilTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");
        LibraryUtil.addBookToLibrary("003", "There was a Country", "Chinua Achebe");
        LibraryUtil.addBookToLibrary("004", "How Will You Measure Your Life?",
                "Clayton M. Christensen");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        bookCardCatalog.clear();
        libraryCardCatalog.clear();
        priorityQueue.clear();
        normalQueue.clear();
    }


    @org.junit.jupiter.api.Nested
    class addBookToLibraryTest {
        @org.junit.jupiter.api.Test
        void addNewBookToLibrary() {
            assertAll(
                    () -> assertTrue(bookCardCatalog.containsKey("purple hibiscus".toLowerCase())),
                    () -> assertTrue(bookCardCatalog.containsKey("Things Fall Apart".toLowerCase())),
                    () -> assertTrue(bookCardCatalog.containsKey("There was a Country".toLowerCase()))
            );
        }
    }


    @org.junit.jupiter.api.Nested
    class borrowBookTest {
        @org.junit.jupiter.api.Test
        void testThatBookIsBorrowedSuccessfully() {
            Student student = new Student("Solomon", "male", "1992-09-21",
                    "07068660641", "001", Grade.CLASS_1);
            String returnText = LibraryUtil.borrowBook(student, "how will you measure your life?");

            assertAll(
                    () -> assertEquals("Book (how will you measure your life?) borrowed successfully by Solomon",
                            returnText),
                    () -> assertTrue(libraryCardCatalog.containsKey(student.getName()))
            );
        }

        @org.junit.jupiter.api.Test
        void testThatBorrowBookHandlesWhenBookIsNotInShelf() {
            Student student1 = new Student("Prince", "male", "1992-09-21",
                    "07068660641", "002", Grade.CLASS_3);
            Student student2 = new Student("Johnbosco", "male", "1992-09-21",
                    "07068660641", "003", Grade.CLASS_5);

            LibraryUtil.borrowBook(student1, "how will you measure your life?");
            String returnText = LibraryUtil.borrowBook(student2, "how will you measure your life?");

            assertAll(
                    () -> assertEquals("BOOK_IS_NOT_ON_SHELF", returnText),
                    () -> assertFalse(libraryCardCatalog.containsKey(student2.getName()))
            );
        }

        @org.junit.jupiter.api.Test
        void testThatBookBookHandlesWhenBookIsNotInLibrary() {
            Student student = new Student("Solomon", "male", "1992-09-21",
                    "07068660641", "001", Grade.CLASS_1);
            String returnText = LibraryUtil.borrowBook(student, "chike and the river");

            assertAll(
                    () -> assertEquals("LIBRARY_DOES_NOT_HAVE_BOOK", returnText),
                    () -> assertFalse(libraryCardCatalog.containsKey(student.getName()))
            );
        }
    }

    @org.junit.jupiter.api.Test
    void testThatPriorityQueueThrowsExceptionForNullUserAndEmptyBookName() {
        Student student = null;
        Staff teacher = new Staff("Emmanuella", "f", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> LibraryUtil.waitOnPriorityQueue(student, "Purple Hibiscus")),
                () -> assertThrows(IllegalArgumentException.class, () -> LibraryUtil.waitOnPriorityQueue(teacher, "")),
                () -> assertThrows(IllegalArgumentException.class, () -> LibraryUtil.waitOnPriorityQueue(teacher, "    "))
        );
    }


    @org.junit.jupiter.api.Test
    void testThatPriorityQueueOrdersByRankPriority() {
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


        LibraryUtil.waitOnPriorityQueue(student1, "Purple Hibiscus");
        LibraryUtil.waitOnPriorityQueue(student3, "Purple Hibiscus");
        LibraryUtil.waitOnPriorityQueue(student2, "Things fall apart");
        LibraryUtil.waitOnPriorityQueue(teacher1, "Things fall apart");
        LibraryUtil.waitOnPriorityQueue(student2, "Purple Hibiscus");
        LibraryUtil.waitOnPriorityQueue(teacher2, "Purple Hibiscus");

        assertAll(
                () -> assertEquals(6, LibraryUtil.priorityQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher2, "purple hibiscus"), LibraryUtil.priorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student3, "purple hibiscus"), LibraryUtil.priorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "purple hibiscus"), LibraryUtil.priorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student1, "purple hibiscus"), LibraryUtil.priorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher1, "things fall apart"), LibraryUtil.priorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "things fall apart"), LibraryUtil.priorityQueue.remove()),
                () -> assertEquals(0, LibraryUtil.priorityQueue.size())
        );
        LibraryUtil.priorityQueue.clear();
    }

    @org.junit.jupiter.api.Test
    void testThatPriorityQueueEntersUniqueEntry() {
        Staff teacher3 = new Staff("David", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.waitOnPriorityQueue(teacher3, "There was a country");
        LibraryUtil.waitOnPriorityQueue(teacher3, "There was a country");

        assertAll(
                () -> assertEquals(1, LibraryUtil.priorityQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher3, "there was a country"), LibraryUtil.priorityQueue.remove())
        );
        LibraryUtil.priorityQueue.clear();
    }

    @org.junit.jupiter.api.Test
    void testThatNormalQueueThrowsExceptionForNullUserAndEmptyBookName() {
        Student student = null;
        Staff teacher = new Staff("Emmanuella", "f", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> LibraryUtil.waitOnNormalQueue(student, "Purple Hibiscus")),
                () -> assertThrows(IllegalArgumentException.class, () -> LibraryUtil.waitOnNormalQueue(teacher, "")),
                () -> assertThrows(IllegalArgumentException.class, () -> LibraryUtil.waitOnNormalQueue(teacher, "    "))
        );
    }

    @org.junit.jupiter.api.Test
    void testThatNormalQueueOrdersByFirstInFirstServe() {
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


        LibraryUtil.waitOnNormalQueue(student1, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(student3, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(student2, "Things fall apart");
        LibraryUtil.waitOnNormalQueue(teacher1, "Things fall apart");
        LibraryUtil.waitOnNormalQueue(student2, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(teacher2, "Purple Hibiscus");

        assertAll(
                () -> assertEquals(6, normalQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student1, "purple hibiscus"), normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student3, "purple hibiscus"), normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "things fall apart"), normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher1, "things fall apart"), normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "purple hibiscus"), normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher2, "purple hibiscus"), normalQueue.remove()),
                () -> assertEquals(0, normalQueue.size())
        );
        normalQueue.clear();
    }

    @org.junit.jupiter.api.Test
    void testThatNormalQueueEntersUniqueEntry() {
        Staff teacher3 = new Staff("David", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.waitOnNormalQueue(teacher3, "There was a country");
        LibraryUtil.waitOnNormalQueue(teacher3, "There was a country");

        assertAll(
                () -> assertEquals(1, normalQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher3, "there was a country"), normalQueue.remove())
        );
        normalQueue.clear();
    }

    @org.junit.jupiter.api.Test
    void testProcessPriorityQueue() {
        LibraryUtil.processPriorityQueue();

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


        LibraryUtil.waitOnPriorityQueue(student1, "Purple Hibiscus");
        LibraryUtil.waitOnPriorityQueue(student3, "Purple Hibiscus");
        LibraryUtil.waitOnPriorityQueue(student2, "Things fall apart");
        LibraryUtil.waitOnPriorityQueue(teacher1, "Things fall apart");
        LibraryUtil.waitOnPriorityQueue(student2, "Purple Hibiscus");
        LibraryUtil.waitOnPriorityQueue(teacher2, "Purple Hibiscus");

        LibraryUtil.processPriorityQueue();

        assertTrue(LibraryUtil.priorityQueue.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testProcessNormalQueue() {
        LibraryUtil.processNormalQueue();

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


        LibraryUtil.waitOnNormalQueue(student1, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(student3, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(student2, "Things fall apart");
        LibraryUtil.waitOnNormalQueue(teacher1, "Things fall apart");
        LibraryUtil.waitOnNormalQueue(student2, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(teacher2, "Purple Hibiscus");

        LibraryUtil.processNormalQueue();

        assertTrue(normalQueue.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testReturnBorrowedBook() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        LibraryUtil.borrowBook(student, "Purple Hibiscus");
        LibraryCard libraryCard = libraryCardCatalog.get(student.getName());


        assertAll(
                () -> assertTrue(libraryCardCatalog.containsKey(student.getName())),
                () -> assertEquals(1, libraryCard.getBorrowedBooks().size(), "There should only be no book"),
                () -> assertTrue(libraryCard.getBorrowedBooks().containsKey("purple hibiscus"), "Library card should remove book added"),
                () -> assertFalse(BookValidatorService.isBookOnShelf().apply("Purple Hibiscus").truthValue),
                () -> assertEquals(1, bookCardCatalog.get("purple hibiscus").getCopiesBorrowed())
        );

        LibraryUtil.returnBorrowedBook(student, "Purple Hibiscus");

        assertAll(
                () -> assertTrue(libraryCardCatalog.containsKey(student.getName())),
                () -> assertEquals(0, libraryCard.getBorrowedBooks().size(), "There should only be no book"),
                () -> assertFalse(libraryCard.getBorrowedBooks().containsKey("purple hibiscus"), "Library card should remove book added"),
                () -> assertTrue(BookValidatorService.isBookOnShelf().apply("Purple Hibiscus").truthValue),
                () -> assertEquals(0, bookCardCatalog.get("purple hibiscus").getCopiesBorrowed())
        );
    }

    @org.junit.jupiter.api.Test
    void collectBookFromShelfTest() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        LibraryUtil.collectBookFromShelf("Purple Hibiscus");
        assertEquals(1, bookCardCatalog.get("purple hibiscus").getCopiesBorrowed());
    }

    @org.junit.jupiter.api.Test
    void testThatValidateUserReturnsApproriateResultForInvalidUser() {
        LibraryUtil.addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");


        Student student1 = null;
        Student student2 = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Staff teacher = new Staff("Daro", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.borrowBook(student2, "Things Fall Apart");

        LibraryUtil.borrowBook(teacher, "Purple Hibiscus");
        LibraryUtil.borrowBook(teacher, "Things fall apart");
        LibraryUtil.borrowBook(teacher, "How Will You Measure Your Life?");

        UserValidatorService.UserValidationResult student1Result = LibraryUtil.validateUser.apply(student1, "How Will You Measure Your Life?");
        UserValidatorService.UserValidationResult student2Result = LibraryUtil.validateUser.apply(student2, "Things Fall Apart");
        UserValidatorService.UserValidationResult staffResult = LibraryUtil.validateUser.apply(teacher, "There was a country");

        assertAll(
                () -> assertFalse(student1Result.truthValue),
                () -> assertEquals(UserValidatorService.UserValidationResult.INVALID_USER, student1Result),
                () -> assertFalse(student2Result.truthValue),
                () -> assertEquals(UserValidatorService.UserValidationResult.USER_ALREADY_BORROWED_SAME_BOOK, student2Result),
                () -> assertFalse(staffResult.truthValue),
                () -> assertEquals(UserValidatorService.UserValidationResult.EXCEEDED_BORROW_LIMIT, staffResult)
        );
    }

    @org.junit.jupiter.api.Test
    void testThatValidateUserReturnsAppropriateResultForValidUsers() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Staff teacher = new Staff("Daro", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.borrowBook(student, "Things Fall Apart");

        UserValidatorService.UserValidationResult studentResult = LibraryUtil.validateUser.apply(student, "Purple Hibiscus");
        UserValidatorService.UserValidationResult staffResult = LibraryUtil.validateUser.apply(teacher, "There was a country");

        assertAll(
                () -> assertTrue(studentResult.truthValue),
                () -> assertEquals(UserValidatorService.UserValidationResult.USER_IS_ELIGIBLE, studentResult),
                () -> assertTrue(staffResult.truthValue),
                () -> assertEquals(UserValidatorService.UserValidationResult.USER_IS_ELIGIBLE, staffResult)
        );
    }
}