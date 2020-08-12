package model;

/**
 *  Student class extends User class
 *  Instantiates more information about a student
 *  implement getRanking method from Rankable interface
 */
public class Student extends User {
    private String matricNo;
    private String grade;

    public Student(String name, String gender, String birthDate, String contact, String matricNo, Grade grade) {
        super(name, gender, birthDate, contact);
        this.matricNo = matricNo;
        this.grade = grade.toString();
    }

    // getters and setters of instance variables
    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // returns the ranking of a student to used for priority queue
    @Override
    public int getRanking() {
        return Grade.valueOfGrade(grade).ordinal();
    }
}
