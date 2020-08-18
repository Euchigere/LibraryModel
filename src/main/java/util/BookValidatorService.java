package util;

import model.BookCard;

import java.util.function.Function;

import static util.BookValidatorService.BookValidationResult;
import static util.BookValidatorService.BookValidationResult.*;
import static util.MOCK_DATABASE.bookCardCatalog;

public interface BookValidatorService extends Function<String, BookValidationResult> {
    static BookValidatorService isValidBookName() {
        return bookName ->
                bookName == null || bookName.isEmpty() ? INVALID_BOOK_NAME
                        : VALID_BOOK_NAME;
    }

    static BookValidatorService libraryHasBook() {
        return bookName ->
                bookCardCatalog.containsKey(bookName.toLowerCase()) ?
                        BOOK_IS_AVAILABLE : LIBRARY_DOES_NOT_HAVE_BOOK;
    }

    static BookValidatorService bookIsOnShelf() {
        return bookName -> {
            BookCard bookCard = bookCardCatalog.get(bookName.toLowerCase());
            return bookCard.getCopiesBorrowed() < bookCard.getTotalNoOfCopies() ?
                    BOOK_IS_AVAILABLE : BOOK_IS_NOT_ON_SHELF;
        };
    }

    enum BookValidationResult {
        BOOK_IS_AVAILABLE(true),
        LIBRARY_DOES_NOT_HAVE_BOOK(false),
        BOOK_IS_NOT_ON_SHELF(false),
        INVALID_BOOK_NAME(false),
        VALID_BOOK_NAME(false);

        boolean truthValue;

        BookValidationResult(boolean truthValue) {
            this.truthValue = truthValue;
        }
    }

    default BookValidatorService and (BookValidatorService other) {
        return bookName -> {
            BookValidationResult result = this.apply(bookName);
            return result.truthValue ? other.apply(bookName) : result;
        };
    }
}
