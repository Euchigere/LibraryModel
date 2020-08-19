package util;

import model.BookCard;
import model.LibraryCard;
import model.User;

import java.util.*;

/**
 * Mock Database
 * Has all the records used by the libraryUtil
 */

public class MOCK_DATABASE {
    // bookCardCatalog contains records of all the book cards
    public static Map<String, BookCard> bookCardCatalog = new HashMap<>();

    // record of all the library cards in the library
    public static Map<String, LibraryCard> libraryCardCatalog = new HashMap<>();

    // Array implemented as queue to save entries
    public static List<Map.Entry<User, String>> priorityQueue = new ArrayList<>();

    // Linked list to save entries in normal queue
    public static Queue<Map.Entry<User, String>> normalQueue = new LinkedList<>();
}
