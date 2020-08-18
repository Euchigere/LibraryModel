package util;

import model.User;

import java.util.function.Function;
import static util.MOCK_DATABASE.libraryCardCatalog;
import static util.UserValidatorService.UserValidationResult;
import static util.UserValidatorService.UserValidationResult.*;

public interface UserValidatorService extends Function<User, UserValidationResult> {

    static UserValidatorService isValidUser() {
        return user ->
                user != null ? VALID_USER : INVALID_USER;
    }

    static UserValidatorService isRegisteredUser() {
        return user ->
                libraryCardCatalog.containsKey(user.getName()) ?
                        REGISTERED_USER : UNREGISTERED_USER;
    }

    static UserValidatorService isEligibleUser() {
        return user -> {
            if (!isRegisteredUser().apply(user).truthValue) {
                return NEW_USER;
            } else {
                return libraryCardCatalog.get(user.getName()).getBorrowedBooks().size() == 3 ?
                        USER_IS_ELIGIBLE : EXCEEDED_BORROW_LIMIT;
            }
        };
    }

    enum UserValidationResult {
        VALID_USER(true),
        INVALID_USER(false),
        REGISTERED_USER(true),
        UNREGISTERED_USER(false),
        USER_IS_ELIGIBLE(true),
        EXCEEDED_BORROW_LIMIT(false),
        NEW_USER(true),
        USER_ALREADY_BORROWED_SAME_BOOK(false);

        boolean truthValue;

        UserValidationResult(boolean truthValue) {
            this.truthValue = truthValue;
        }
    }

    default UserValidatorService and (UserValidatorService other) {
        return user -> {
            UserValidationResult result = this.apply(user);
            return result.truthValue ? other.apply(user) : result;
        };
    }
}
