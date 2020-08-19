import model.*;
import util.LibraryUtil;

import static util.LibraryUtil.*;

public class Main {

    public static void main(String[] args) {
        // create user instances
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

        // add books to library
        addBookToLibrary("001", "Purple Hibiscus", "Chimamanda Adichie");
        addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");
        addBookToLibrary("003", "There was a Country", "Chinua Achebe");
        addBookToLibrary("004", "How Will You Measure Your Life?",
                "Clayton M. Christensen");

        // queue users on priority queue
        waitOnPriorityQueue(student1, "Principles");
        waitOnPriorityQueue(student3, "Purple Hibiscus");
        waitOnPriorityQueue(student2, "Things fall apart");
        waitOnPriorityQueue(teacher1, "Things fall apart");
        waitOnPriorityQueue(student2, "Purple Hibiscus");
        waitOnPriorityQueue(teacher2, "Purple Hibiscus");

        System.out.println("+++++++++ Priority Queue ++++++++++");
        processPriorityQueue();

        // users returned books borrowed successfully
        LibraryUtil.returnBorrowedBook(teacher2, "Purple Hibiscus");
        LibraryUtil.returnBorrowedBook(teacher1, "Things fall apart");

        // queue users on normal queue
        LibraryUtil.waitOnNormalQueue(student1, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(student3, "Why Nations Fail");
        LibraryUtil.waitOnNormalQueue(student2, "Things fall apart");
        LibraryUtil.waitOnNormalQueue(teacher1, "Things fall apart");
        LibraryUtil.waitOnNormalQueue(student2, "Purple Hibiscus");
        LibraryUtil.waitOnNormalQueue(teacher2, "Purple Hibiscus");

        System.out.println("\n+++++++++ Normal Queue ++++++++++");
        processNormalQueue();
    }

}
