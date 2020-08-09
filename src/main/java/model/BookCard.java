package model;

public class BookCard {
    private String ISBN;
    private String bookName;
    private String authorName;
    private int totalNoOfCopies;
    private int noOfCopiesBorrowed;

    public BookCard(String ISBN, String bookName, String authorName, int totalNoOfCopies) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.authorName = authorName;
        this.totalNoOfCopies = totalNoOfCopies;
        this.noOfCopiesBorrowed = 0;
    }
}
