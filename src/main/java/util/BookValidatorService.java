package util;

import model.BookCard;

import java.util.function.Function;

import static util.BookValidatorService.BookValidationResult;
import static util.BookValidatorService.BookValidationResult.*;
import static util.MOCK_DATABASE.bookCardCatalog;

/**
 * BookValidationService interface
 */
public interface BookValidatorService extends Function<String, BookValidationResult> {
    // checks if book name is null or empty
    static BookValidatorService isValidBookName() {
        return bookName ->
                bookName == null || bookName.isBlank() ? INVALID_BOOK_NAME
                        : VALID_BOOK_NAME;
    }

    // validates that library has copy of book
    static BookValidatorService libraryHasBook() {
        return bookName ->
                bookCardCatalog.containsKey(bookName.toLowerCase()) ?
                        BOOK_IS_AVAILABLE : LIBRARY_DOES_NOT_HAVE_BOOK;
    }

    // validate book is on library shelf
    static BookValidatorService isBookOnShelf() {
        return bookName -> {
            BookCard bookCard = bookCardCatalog.get(bookName.toLowerCase());
            return bookCard.getCopiesBorrowed() < bookCard.getTotalNoOfCopies() ?
                    BOOK_IS_AVAILABLE : BOOK_IS_NOT_ON_SHELF;
        };
    }

    // book validation result
    enum BookValidationResult {
        BOOK_IS_AVAILABLE(true),
        LIBRARY_DOES_NOT_HAVE_BOOK(false),
        BOOK_IS_NOT_ON_SHELF(false),
        INVALID_BOOK_NAME(false),
        VALID_BOOK_NAME(true);

        boolean truthValue;

        BookValidationResult(boolean truthValue) {
            this.truthValue = truthValue;
        }
    }

    // implement validation combinator for bookValidatorService
    default BookValidatorService and (BookValidatorService other) {
        return bookName -> {
            BookValidationResult result = this.apply(bookName);
            return result.truthValue ? other.apply(bookName) : result;
        };
    }
}
