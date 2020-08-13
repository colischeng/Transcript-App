package model;

import exceptions.UnattainableException;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

// Represents the list of courses that have been added to the transcript

public class Transcript {

    private final List<Course> courseList;

    // EFFECTS: construct new Transcript object
    public Transcript() {
        courseList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a course onto the transcript
    public void addCourse(Course course) {
        courseList.add(course);
    }

    // EFFECTS:  returns the cumulative GPA (weighted by credits) on the transcript so far
    public int cumulativeGPA() {
        int sum = 0;
        int total = 0;
        for (Course c : courseList) {
            sum += (c.getGrade() * c.getCredits());
            total += (100 * c.getCredits());
        }
        return (sum * 100) / total;
    }

    // REQUIRES: 0 <= goal <= 100
    // EFFECTS: if target grade is achievable (no negative grades or grades above 100)
    //         - return grade needed in the next 3 credit courses to reach target
    //         - otherwise, return -1
    public int target(int goal) throws UnattainableException {
        int sum = 0;
        int total = 0;
        for (Course c : courseList) {
            sum += (c.getGrade() * c.getCredits());
            total += (100 * c.getCredits());
        }
        int val = (((goal * (total + 300)) / 100) - sum) / 3;
        if (0 > val || val > 100) {
            System.out.println("\nThis GPA is not attainable.");
            throw new UnattainableException();
        } else {
            System.out.println("\nYou need to score " + val + " in your next 3-credit course.");
            return val;
        }

    }

    // REQUIRES: transcript is not empty, n> 0
    // MODIFIES: this
    // EFFECTS: removes the nth course transcript
    public void removeCourse(int n) {
        int index = (n - 1);
        courseList.remove(index);
    }

    // EFFECTS: return the number of courses on transcript
    public int length() {
        return courseList.size();
    }

    // EFFECTS: return the CourseListObject
    public List<Course> getCourseList() {
        return courseList;
    }
}
