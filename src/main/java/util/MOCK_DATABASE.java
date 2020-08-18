package util;

import model.BookCard;
import model.LibraryCard;
import model.User;

import java.util.*;

public class MOCK_DATABASE {
    public static Map<String, BookCard> bookCardCatalog = new HashMap<>();
    public static Map<String, LibraryCard> libraryCardCatalog = new HashMap<>();
    public static List<Map.Entry<User, String>> priorityQueue = new ArrayList<>();
    public static Queue<Map.Entry<User, String>> normalQueue = new LinkedList<>();
}
