package util;

import model.*;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.AbstractMap;

/**
 * LibraryCardUtil contains controller methods for the library model
 * It is composed of LibraryCaryUtil, BookCardUtil and MyPriorityQueue custom classes
 */


public class LibraryUtil {

    // List of those that want to borrow books
    public static BookCardUtil bookCardUtil = new BookCardUtil();

    public static LibraryCardUtil libraryCardUtil = new LibraryCardUtil();

    public static MyPriorityQueue priorityQueue = new MyPriorityQueue();
    public static Queue<Map.Entry<User, String>> normalQueue = new LinkedList<>();

    public static void addBookToLibrary(String ISBN, String bookName, String authorName) {
        bookCardUtil.addBook(ISBN, bookName, authorName);
    }

    public static void waitOnPriorityQueue(User user, String bookName) {
        priorityQueue.add(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()));
    }

    public static void waitOnNormalQueue(User user, String bookName) {
        if (normalQueue.contains(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()))) {
            return;
        }
        normalQueue.add(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()));
    }

    public static void processPriorityQueue() {
        if (priorityQueue.isEmpty()) {
            System.out.println("Empty Queue");
            return;
        }
        while (!priorityQueue.isEmpty()) {
            Map.Entry<User, String> simpleEntry = priorityQueue.remove();
            User user = simpleEntry.getKey();
            String bookName = simpleEntry.getValue();
            borrowBook(user, bookName);
        }
    }

    public static void processNormalQueue() {
        if (normalQueue.isEmpty()) {
            System.out.println("Empty Queue");
            return;
        }
        while (!normalQueue.isEmpty()) {
            Map.Entry<User, String> simpleEntry = normalQueue.remove();
            User user = simpleEntry.getKey();
            String bookName = simpleEntry.getValue();
            borrowBook(user, bookName);
        }
    }

    public static String borrowBook(User user, String bookName) {
        if (!bookCardUtil.containsBook(bookName)) {
            return "book is not in the library";
        } else if (!bookCardUtil.isOnShelf(bookName)) {
            return "book taken";
        } else {
            bookCardUtil.collectBookFromShelf(bookName);
            libraryCardUtil.addBorrowedBook(user, bookName);
            return "book successfully borrowed";
        }
    }

    public static void returnBorrowedBook(User user, String bookName) {
        libraryCardUtil.returnBook(user, bookName);
    }
}
