package util;

import model.Grade;
import model.Staff;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.junit.jupiter.api.Assertions.*;

class MyPriorityQueueTest {
    MyPriorityQueue myPriorityQueue;

    @BeforeEach
    void setUp() {
        myPriorityQueue = new MyPriorityQueue();
    }

    @Test
    void testThatAddMethodOrderElementByRanking() {
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


        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student1, "purple hibiscus"));
        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student3, "purple hibiscus"));
        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student2, "things fall apart"));
        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(teacher1, "things fall apart"));
        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student2, "purple hibiscus"));
        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(teacher2, "purple hibiscus"));

        assertAll(
                () -> assertEquals(6, myPriorityQueue.size()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher2, "purple hibiscus"), myPriorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student3, "purple hibiscus"), myPriorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "purple hibiscus"), myPriorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student1, "purple hibiscus"), myPriorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(teacher1, "things fall apart"), myPriorityQueue.remove()),
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student2, "things fall apart"), myPriorityQueue.remove()),
                () -> assertEquals(0, LibraryUtil.priorityQueue.size())
        );
    }

    @Test
    void testIsEmptyMethod() {
        assertTrue(myPriorityQueue.isEmpty());
    }

    @Test
    void testRemoveMethod() {
        Student student = new Student("Solomon", "male", "1992-09-21",
                "07068660641", "001", Grade.CLASS_1);

        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student, "purple hibiscus"));

        assertEquals(new AbstractMap.SimpleImmutableEntry<>(student, "purple hibiscus"), myPriorityQueue.remove());
    }

    @Test
    void testPeekMethod() {
        Student student = new Student("Solomon", "male", "1992-09-21",
                "07068660641", "001", Grade.CLASS_1);

        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student, "purple hibiscus"));

        assertAll(
                () -> assertEquals(new AbstractMap.SimpleImmutableEntry<>(student, "purple hibiscus"), myPriorityQueue.peek()),
                () -> assertTrue(myPriorityQueue.getQueue().contains(new AbstractMap.SimpleEntry<>(student, "purple hibiscus"))),
                () -> assertEquals(1, myPriorityQueue.size())
        );
    }

    @Test
    void testClearMethod() {
        Student student = new Student("Solomon", "male", "1992-09-21",
                "07068660641", "001", Grade.CLASS_1);

        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student, "purple hibiscus"));
        myPriorityQueue.clear();

        assertTrue(myPriorityQueue.isEmpty());
    }

    @Test
    void testSizeMethod() {
        Student student = new Student("Solomon", "male", "1992-09-21",
                "07068660641", "001", Grade.CLASS_1);

        myPriorityQueue.add(new AbstractMap.SimpleEntry<>(student, "purple hibiscus"));

        assertEquals(1, myPriorityQueue.size());
    }
}