package model;

import java.time.LocalDate;

public class Student extends User {
    private String matricNo;
    private String grade;

    public Student(String name, String gender, String birthDate, String contact, String matricNo, Grade grade) {
        super(name, gender, birthDate, contact);
        this.matricNo = matricNo;
        this.grade = grade.toString();
    }

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

    @Override
    public int getRanking() {
        //System.out.println(Grade.valueOfGrade(grade).ordinal());
        return Grade.valueOfGrade(grade).ordinal();
    }
}
