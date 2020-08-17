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

    // Static instance of BookCardUtil, LibraryCardUtil, MyPriorityQueue and NormalQueue
    public static BookCardUtil bookCardUtil = new BookCardUtil();

    public static LibraryCardUtil libraryCardUtil = new LibraryCardUtil();

    public static MyPriorityQueue priorityQueue = new MyPriorityQueue();
    public static Queue<Map.Entry<User, String>> normalQueue = new LinkedList<>();

    // Method add new book to library's book collection or updates copy of old books
    public static void addBookToLibrary(String ISBN, String bookName, String authorName) {
        bookCardUtil.createBookCard(ISBN, bookName, authorName);
    }

    // user is added to priorityQueue with the name of the book user wants to borrow
    public static void waitOnPriorityQueue(User user, String bookName) {
        priorityQueue.add(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()));
    }

    // user is added to priorityQueue with the name of the book user wants to borrow
    public static void waitOnNormalQueue(User user, String bookName) {
        if (normalQueue.contains(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()))) {
            return;
        }
        normalQueue.add(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()));
    }

    // queues in the priorityQueue is processed and handled accordingly
    public static void processPriorityQueue() {
        if (priorityQueue.isEmpty()) {
            System.out.println("Empty Queue");
            return;
        }
        priorityQueue
                .getQueue()
                .forEach((entry) -> borrowBook(entry.getKey(), entry.getValue()));
        priorityQueue.clear();
    }

    // queues in the priorityQueue is processed and handled accordingly
    public static void processNormalQueue() {
        if (normalQueue.isEmpty()) {
            System.out.println("Empty Queue");
            return;
        }
        normalQueue.forEach((entry) -> borrowBook(entry.getKey(), entry.getValue()));
        normalQueue.clear();
    }

    // book is lent to user if book is available and user is eligible
    public static String borrowBook(User user, String bookName) {
        if (!bookCardUtil.containsBook(bookName)) {
            return "book is not in the library";
        } else if (!bookCardUtil.isOnShelf(bookName)) {
            return "book taken";
        } else if (libraryCardUtil.isEligible(user, bookName)) {
            bookCardUtil.collectBookFromShelf(bookName);
            libraryCardUtil.addToLibraryCard(user, bookName);
            return "book successfully borrowed";
        } else {
            return "User is not eligible";
        }

    }

    // modifies borrowed books in user's library card if user returns book
    public static void returnBorrowedBook(User user, String bookName) {
        libraryCardUtil.returnBook(user, bookName);
    }
}
