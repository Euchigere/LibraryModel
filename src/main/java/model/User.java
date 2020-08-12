package model;

import util.Rankable;
import java.time.LocalDate;

/**
 *  User abstract class implements Rankable interface
 *  Instantiates basic information of a user
 */
public abstract class User implements Rankable {
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String contact;

    // enum constant for user gender
    private enum Gender {
        MALE("Male"), FEMALE("Female");

        private final String gender;
        Gender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }

    }

    User(String name, String gender, String birthDate, String contact) {
        this.name = name;
        gender = gender.toLowerCase();
        if ("male".equals(gender) || "m".equals(gender)) {
            this.gender = Gender.MALE.toString();
        } else if ("female".equals(gender) || "f".equals(gender)) {
            this.gender = Gender.FEMALE.toString();
        } else {
            this.gender = "[No Data]";
        }
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (Exception e) {
            System.err.println("Wrong date format entered. use this format -> yyyy-mm-dd");
            this.birthDate = null;
        }
        this.contact = contact;
    }

    // getters and setters of instance variables
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if ("male".equals(gender) || "m".equals(gender)) {
            this.gender = Gender.MALE.toString();
        } else if ("female".equals(gender) || "f".equals(gender)) {
            this.gender = Gender.FEMALE.toString();
        } else {
            this.gender = "[No Data]";
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (Exception e) {
            System.err.println("Wrong date format entered. use this format -> yyyy-mm-dd");
            this.birthDate = null;
        }
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
