package model;

import java.util.List;

public class Course {

    private final String type;
    private final Integer code;
    private final Integer yearTaken;
    private final Integer grade;
    private final Integer credits;

    public Course(String type,
                  Integer code,
                  Integer yearTaken,
                  Integer grade,
                  Integer credits) {

        this.type = type;
        this.code = code;
        this.yearTaken = yearTaken;
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

    //EFFECTS: get a course's academic year
    public Integer getYearTaken() {
        return yearTaken;
    }

    //EFFECTS: get a course's grade
    public Integer getGrade() {
        return grade;
    }

    //EFFECTS: get a course's credit load
    public Integer getCredits() {
        return credits;
    }


}
