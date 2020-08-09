package model;

public class Student extends PersonBio {
    private String matricNo;
    private String grade;

    public Student(String name, String gender, String birthDate, String contact, String matricNo, String grade) {
        super(name, gender, birthDate, contact);
        this.matricNo = matricNo;
        this.grade = grade;
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
}
