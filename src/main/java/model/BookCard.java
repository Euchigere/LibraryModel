package model;

public class BookCard {
    private String ISBN;
    private String bookName;
    private String authorName;
    private int totalNoOfCopies;
    private int copiesBorrowed;

    public BookCard(String ISBN, String bookName, String authorName) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.authorName = authorName;
        this.totalNoOfCopies = 1;
        this.copiesBorrowed = 0;
    }

    public int getTotalNoOfCopies() {
        return totalNoOfCopies;
    }

    public void addMoreCopy() {
        totalNoOfCopies += 1;
    }

    public boolean isOnShelf() {
        return copiesBorrowed < totalNoOfCopies;
    }
}
