package model;

public class Course {

    private final String type;
    private final Integer code;
    private final Integer grade;
    private final Integer credits;

    public Course(String type,
                  Integer code,
                  Integer grade,
                  Integer credits) {

        this.type = type;
        this.code = code;
        this.grade = grade;
        this.credits = credits;
    }

    //EFFECTS: get a course's type
    public String getType() {
        return type;
    }

    //EFFECTS: get a course's code
    public Integer getCode() {
        return code;
    }

    //EFFECTS: get a course's grade
    public Integer getGrade() {
        return grade;
    }

    //EFFECTS: get a course's credit load
    public Integer getCredits() {
        return credits;
    }


    public String toString() {
        return "Type: " + getType() + " || "
                + "Code: " + getCode() + " || "
                + "Grade: " + getGrade() + " || "
                + "Credits: " + getCredits();
    }


}
