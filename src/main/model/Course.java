package model;

//Items of interest that represent the classes taken

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;

public class Course implements Saveable {
    private static int nextCourseId = 0;  // tracks id of next course created
    private int id;                       // course id
    private final String type;            // department of course
    private final Integer code;           // course number
    private final Integer grade;          // grade received in course
    private final Integer credits;        // number of credits teh course is worth

    // EFFECTS: construct new Course object
    public Course(String type,
                  Integer code,
                  Integer grade,
                  Integer credits) {
        id = nextCourseId++;
        this.type = type;
        this.code = code;
        this.grade = grade;
        this.credits = credits;
    }

    /*
     * REQUIRES: name has a non-zero length; code, grade, and credits >= 0
     * EFFECTS: constructs a course with id, name and balance;
     * nextCourseId is the id of the next course to be constructed
     * NOTE: this constructor is to be used only when constructing
     * a course from data stored in file
     */
    public Course(int nextId,
                  int id,
                  String type,
                  Integer code,
                  Integer grade,
                  Integer credits) {
        nextCourseId = nextId;
        this.id = id;
        this.type = type;
        this.code = code;
        this.grade = grade;
        this.credits = credits;
    }

    //EFFECTS: get a course's ID
    public int getId() {
        return id;
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

    //EFFECTS: create a printed line of info for a course object
    public String toString() {
        return "Type: " + getType() + " || "
                + "Code: " + getCode() + " || "
                + "Grade: " + getGrade() + " || "
                + "Credits: " + getCredits();
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(nextCourseId);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(id);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(type);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(code);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(grade);
        printWriter.print(Reader.DELIMITER);
        printWriter.println(credits);
    }
}
