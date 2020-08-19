package util;

import model.Grade;
import model.Staff;
import model.Student;

import static util.MOCK_DATABASE.*;
import static util.MOCK_DATABASE.normalQueue;
import static util.UserValidatorService.*;
import static util.UserValidatorService.UserValidationResult.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorServiceTest {
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        bookCardCatalog.clear();
        libraryCardCatalog.clear();
    }

    @Test
    void testIsValidUser() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Staff teacher = null;

        assertAll(
                () -> assertEquals(INVALID_USER, isValidUser().apply(teacher)),
                () -> assertEquals(VALID_USER, isValidUser().apply(student))
        );
    }

    @Test
    void testIsRegisteredUser() {
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");

        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Staff teacher = new Staff("Daro", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.borrowBook(teacher, "Things Fall Apart");

        assertAll(
                () -> assertEquals(UNREGISTERED_USER, isRegisteredUser().apply(student)),
                () -> assertEquals(REGISTERED_USER, isRegisteredUser().apply(teacher))
        );
    }

    @Test
    void testIsEligibleUser() {
        LibraryUtil.addBookToLibrary("002", "Things Fall Apart", "Chinua Achebe");

        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Staff teacher = new Staff("Daro", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        LibraryUtil.borrowBook(teacher, "Things Fall Apart");

        assertAll(
                () -> assertEquals(NEW_USER, isEligibleUser().apply(student)),
                () -> assertEquals(USER_IS_ELIGIBLE, isEligibleUser().apply(teacher))
        );
    }

    @Test
    void testPatternCombinator() {
        Student student = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Staff teacher = null;

        UserValidationResult result1 = isValidUser().and(isRegisteredUser()).apply(student);
        UserValidationResult result2 = isValidUser().and(isRegisteredUser()).apply(teacher);
        UserValidationResult result3 = isValidUser().and(isEligibleUser()).apply(student);
        UserValidationResult result4 = isEligibleUser().and(isRegisteredUser()).apply(student);

        assertAll(
                () -> assertEquals(UNREGISTERED_USER, result1),
                () -> assertEquals(INVALID_USER, result2),
                () -> assertEquals(NEW_USER, result3),
                () -> assertEquals(UNREGISTERED_USER, result4)
        );
    }
}