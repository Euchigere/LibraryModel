package model;

public class Staff extends PersonBio {
    private String staffType;
    private String role;
    private double salary;

    public Staff(String name, String gender, String birthDate, String contact, String staffType, String role, double salary) {
        super(name, gender, birthDate, contact);
        this.staffType = staffType;
        this.role = role;
        this.salary = salary;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
