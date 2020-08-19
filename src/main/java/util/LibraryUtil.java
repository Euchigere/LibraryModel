package util;

import model.*;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Optional;
import java.util.function.BiFunction;

import static util.MOCK_DATABASE.*;
import static util.BookValidatorService.*;
import static util.UserValidatorService.*;

/**
 * LibraryCardUtil contains controller methods for the library model
 * It handles all the library logic
 * Uses User and Book validation services
 */


public class LibraryUtil {

    // static instance MyPriorityQueue
    public static MyPriorityQueue priorityQueue = new MyPriorityQueue();

    // Method adds new book to library's book collection or updates copy of old books
    public static void addBookToLibrary(String ISBN, String bookName, String authorName) {
        BookCard bookCard;
        if (libraryHasBook().apply(bookName).truthValue) {
            bookCard = bookCardCatalog.get(bookName.toLowerCase());
            bookCard.setTotalNoOfCopies(bookCard.getTotalNoOfCopies() + 1);
        } else {
            bookCard = new BookCard(ISBN, bookName, authorName);
            bookCardCatalog.put(bookName.toLowerCase(), bookCard);
        }
    }

    // user is added to priorityQueue with the name of the book user wants to borrow
    public static void waitOnPriorityQueue(User user, String bookName) throws IllegalArgumentException {
        if (!isValidUser().apply(user).truthValue) {
            throw new IllegalArgumentException("User is null");
        } else if (!isValidBookName().apply(bookName).truthValue) {
            throw new IllegalArgumentException("Book name is null or empty");
        }
        priorityQueue.add(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()));
    }

    // user is added to priorityQueue with the name of the book user wants to borrow
    public static void waitOnNormalQueue(User user, String bookName)throws IllegalArgumentException {
        if (!isValidUser().apply(user).truthValue) {
            throw new IllegalArgumentException("User is null");
        } else if (!isValidBookName().apply(bookName).truthValue) {
            throw new IllegalArgumentException("Book name is null or empty");
        }

        // ensure only unique entries are added to queue
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
        // book is validated
        BookValidationResult bookResult = libraryHasBook()
                .and(isBookOnShelf())
                .apply(bookName);
        if (!bookResult.truthValue) {
            System.out.println(bookResult.toString() + " (" + bookName + ")");
            return bookResult.toString();
        }

        // user is validated
        UserValidationResult userResult = validateUser.apply(user, bookName);

        if (!userResult.truthValue) {
            System.out.println(userResult.toString()
                    + " (" + Optional.of(user.getName()).orElse("Null user")
                    + ")");
            return userResult.toString();
        } else {
            // validation successful
            collectBookFromShelf(bookName);
            addToLibraryCard(user, bookName);
            System.out.println("Book (" + bookName + ") borrowed successfully by " + user.getName());
            return "Book (" + bookName + ") borrowed successfully by " + user.getName();
        }
    }

    // book is removed from book catalog and the number of borrowed copy is updated
    public static void collectBookFromShelf(String bookName) {
        bookName = bookName.toLowerCase();
        BookCard bookCard = bookCardCatalog.get(bookName);
        bookCard.setCopiesBorrowed(bookCard.getCopiesBorrowed() + 1);
    }

    // details of book borrowed and date is updated in user's library card
    public static void addToLibraryCard(User user, String bookName) {
        bookName = bookName.toLowerCase();
        LibraryCard libraryCard;

        // if new user, create new library card
        if (!isRegisteredUser().apply(user).truthValue) {
            libraryCard = new LibraryCard(user);
            libraryCardCatalog.put(user.getName(), libraryCard);
        }
        libraryCard = libraryCardCatalog.get(user.getName());
        // stores record of book borrowed and date in users library card
        libraryCard
                .getBorrowedBooks()
                .put(bookName, LocalDate.now());
    }

    // modifies borrowed books in user's library card if user returns book
    public static void returnBorrowedBook(User user, String bookName) throws IllegalArgumentException {
        if (!isValidUser().apply(user).truthValue) {
            throw new IllegalArgumentException("User is null");
        } else if (!isValidBookName().apply(bookName).truthValue) {
            throw new IllegalArgumentException("Book name is null or empty");
        }
        bookName = bookName.toLowerCase();
        BookCard bookCard = bookCardCatalog.get(bookName);
        bookCard.setCopiesBorrowed(bookCard.getCopiesBorrowed() - 1);

        libraryCardCatalog
                .get(user.getName())
                .getBorrowedBooks()
                .remove(bookName);
    }

    // validates a user is eligible to borrow book
    public static BiFunction<User, String, UserValidationResult> validateUser = (user, bookName) -> {
        // check if user value is null or user has exceeded borrow limit
        UserValidationResult userResult = isValidUser()
                .and(isEligibleUser()).apply(user);

        // user has exceeded borrow limit or user value is null
        if (!userResult.truthValue) {
            return userResult;
        }

        // if user is a new user
        if (userResult.equals(UserValidationResult.NEW_USER)) {
            return UserValidationResult.USER_IS_ELIGIBLE;
        }

        // if user is a registered user, check if user already has copy of same book
        if (libraryCardCatalog.get(user.getName()).getBorrowedBooks().containsKey(bookName.toLowerCase())) {
            return UserValidationResult.USER_ALREADY_BORROWED_SAME_BOOK;
        } else {
            return UserValidationResult.USER_IS_ELIGIBLE;
        }
    };
}
