package model;

/**
 *  Staff class extends User class
 *  Instantiates more information about a staff
 *  implement getRanking method from Rankable interface
 */


public class Staff extends User {
    private String staffType;
    private String role;
    private double salary;

    public Staff(String name, String gender, String birthDate, String contact, String staffType, String role, double salary) {
        super(name, gender, birthDate, contact);
        this.staffType = staffType;
        this.role = role;
        this.salary = salary;
    }

    // getters and setters of instance variable
    public String getStaffType() {
        return staffType;
    }

    private void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getRole() {
        return role;
    }

    private void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // returns the ranking of a staff to used for priority queue
    @Override
    public int getRanking() {
        return "Academic".equals(getStaffType()) ? Grade.CLASS_6.ordinal() + 1 : Grade.CLASS_1.ordinal();
    }
}
