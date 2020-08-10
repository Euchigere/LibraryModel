package util;

import model.*;

import java.util.*;

public class LibraryUtil {

    // List of those that want to borrow books
    public static Map<String, BookCard> bookCatalog = new HashMap<>();
    public static Map<String, LibraryCard> borrowers = new HashMap<>();

    public static MyPriorityQueue waitingList = new MyPriorityQueue();

//    public static PriorityQueue<Map.Entry<User, String>> waitingList = new PriorityQueue<>(11, (map1, map2) -> {
////        System.out.println(user1.getName());
////        System.out.println(user2.getName());
//        if (!map2.getValue().equals(map1.getValue())) {
//            // System.out.println("book is not equal: " + map1.get(user1) + " " + map2.get(user2));
//            return 0;
//        } else {
//            return Integer.compare(map2.getKey().getRanking(), map1.getKey().getRanking());
//        }
////        System.out.println("user1: " + user1.getName() + " rank: " + user1.rank());
////        System.out.println("user2: " + user2.getName() + " rank: " + user2.rank());
//
//    });

    public static void addBookToLibrary(String ISBN, String bookName, String authorName) {
        BookCard book;
        if (bookCatalog.containsKey(bookName.toLowerCase())) {
            book = bookCatalog.get(bookName);
            book.addMoreCopy();
        } else {
            book = new BookCard(ISBN, bookName, authorName);
            bookCatalog.put(bookName.toLowerCase(), book);
        }

    }

    public static void borrowBook(User user, String bookName) {
        bookName = bookName.toLowerCase();
        if (!bookCatalog.containsKey(bookName)) {
            System.err.printf("Sorry %s, the book \"%s\" is not in the library",
                user.getName(), bookName);
        } else if (!bookCatalog.get(bookName).isOnShelf()) {
            System.err.printf("Sorry %s, the book \"%s\" has been borrowed out",
                    user.getName(), bookName);
        } else {
            waitingList.add(new AbstractMap.SimpleEntry<>(user, bookName.toLowerCase()));
        }
    }

    public static void processWaitingList() {

    }


}
