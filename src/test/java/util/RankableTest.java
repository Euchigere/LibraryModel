package util;

import model.Grade;
import model.Staff;
import model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankableTest {
    @Test
    void getRanking() {
        Rankable student1 = new Student("Solomon", "male", "1992-09-21",
                "07068660641", "001", Grade.CLASS_1);
        Rankable student2 = new Student("Prince", "male", "1992-09-21",
                "07068660641", "002", Grade.CLASS_3);
        Rankable student3 = new Student("Johnbosco", "male", "1992-09-21",
                "07068660641", "003", Grade.CLASS_5);
        Rankable teacher1 = new Staff("Emmanuella", "f", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);
        Rankable teacher2 = new Staff("Daro", "m", "1992-09-21",
                "07068660641", "Academic", "Teacher", 0.0);

        assertAll(
                () -> assertEquals(0, student1.getRanking()),
                () -> assertEquals(2, student2.getRanking()),
                () -> assertEquals(4, student3.getRanking()),
                () -> assertEquals(6, teacher1.getRanking()),
                () -> assertEquals(6, teacher2.getRanking())
        );

    }
}