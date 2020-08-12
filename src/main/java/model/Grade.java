package model;

/**
 * Enum constants for the grades(class) of the student
 */
public enum Grade {
    CLASS_1("Js1"),
    CLASS_2("Js2"),
    CLASS_3("Js3"),
    CLASS_4("Ss1"),
    CLASS_5("Ss2"),
    CLASS_6("Ss3");

    String grade;
    Grade(String grade) {
        this.grade = grade;
    }

    // returns the enum object of a grade
    public static Grade valueOfGrade(String grade) {
        for (Grade g : Grade.values()) {
            if (g.grade.equals(grade)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return grade;
    }
}
