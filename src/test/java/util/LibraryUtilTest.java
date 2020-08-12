package util;

import model.*;

import java.util.AbstractMap;

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
        LibraryUtil.bookCardUtil = new BookCardUtil();
        LibraryUtil.libraryCardUtil = new LibraryCardUtil();
    }


    @org.junit.jupiter.api.Nested
    class addBookToLibraryTest {
        @org.junit.jupiter.api.Test
        void addNewBookToLibrary() {
            assertAll(
                    () -> assertTrue(LibraryUtil.bookCardUtil.containsBook("purple hibiscus".toLowerCase())),
                    () -> assertTrue(LibraryUtil.bookCardUtil.containsBook("Things Fall Apart".toLowerCase())),
                    () -> assertTrue(LibraryUtil.bookCardUtil.containsBook("There was a Country".toLowerCase()))
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
                    () -> assertEquals("book successfully borrowed", returnText),
                    () -> assertTrue(LibraryUtil.libraryCardUtil.hasUser(student.getName()))
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
                    () -> assertEquals("book taken", returnText),
                    () -> assertFalse(LibraryUtil.libraryCardUtil.hasUser(student2.getName()))
            );
        }

        @org.junit.jupiter.api.Test
        void testThatBookBookHandlesWhenBookIsNotInLibrary() {
            Student student = new Student("Solomon", "male", "1992-09-21",
                    "07068660641", "001", Grade.CLASS_1);
            String returnText = LibraryUtil.borrowBook(student, "chike and the river");

            assertAll(
                    () -> assertEquals("book is not in the library", returnText),
                    () -> assertFalse(LibraryUtil.libraryCardUtil.hasUser(student.getName()))
            );
        }
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
                () -> assertEquals(6, LibraryUtil.normalQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student1, "purple hibiscus"), LibraryUtil.normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student3, "purple hibiscus"), LibraryUtil.normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "things fall apart"), LibraryUtil.normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher1, "things fall apart"), LibraryUtil.normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "purple hibiscus"), LibraryUtil.normalQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher2, "purple hibiscus"), LibraryUtil.normalQueue.remove()),
                () -> assertEquals(0, LibraryUtil.normalQueue.size())
        );
        LibraryUtil.normalQueue.clear();
    }

    @org.junit.jupiter.api.Test
    void testThatNormalQueueEntersUniqueEntry() {
        Staff teacher3 = new Staff("David", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.waitOnNormalQueue(teacher3, "There was a country");
        LibraryUtil.waitOnNormalQueue(teacher3, "There was a country");

        assertAll(
                () -> assertEquals(1, LibraryUtil.normalQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher3, "there was a country"), LibraryUtil.normalQueue.remove())
        );
        LibraryUtil.normalQueue.clear();
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

        assertTrue(LibraryUtil.normalQueue.isEmpty());
    }
}