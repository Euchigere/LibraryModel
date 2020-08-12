package model;

/**
 * BookCard class
 * Contains information about a particular book in a library,
 *      the total no of copies and also the no of copies borrowed(lent)
 */
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

    // getters and setters of all the fields
    public String getBookName() {
        return bookName;
    }

    public int getTotalNoOfCopies() {
        return totalNoOfCopies;
    }

    public void setTotalNoOfCopies(int totalNoOfCopies) {
        this.totalNoOfCopies = totalNoOfCopies;
    }

    public int getCopiesBorrowed() {
        return copiesBorrowed;
    }

    public void setCopiesBorrowed(int copiesBorrowed) {
        this.copiesBorrowed = copiesBorrowed;
    }
}
