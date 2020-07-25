package model;

import java.util.ArrayList;
import java.util.List;

public class Transcript {

    private final List<Course> courseList;

    public Transcript() {
        courseList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a course onto the transcript
    public void addCourse(Course course) {
        courseList.add(course);
    }

    // REQUIRES:
    // MODIFIES:
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

    // REQUIRES: goal >= 0
    // MODIFIES:
    // EFFECTS: if target grade is achievable (no negative grades or grades above 100)
    //         - return grade needed in the next 3 credit courses to reach target
    //         - otherwise, return -1
    public int target(int goal) {
        int sum = 0;
        int total = 0;
        for (Course c : courseList) {
            sum += (c.getGrade() * c.getCredits());
            total += (100 * c.getCredits());
        }
        int val = (((goal * (total + 300)) / 100) - sum) / 3;
        if (0 <= val && val <= 100) {
            return val;
        } else {
            return -1;
        }
      
    }

    // REQUIRES: transcript is not empty
    // MODIFIES: this
    // EFFECTS: removes the last courses added onto transcript
    public void removeCourse() {
        int index = (courseList.size() - 1);
        courseList.remove(index);
    }


    // MODIFIES:
    // EFFECTS: return the number of courses on transcript
    public int length() {
        return courseList.size();
    }

    // EFFECTS: return the CourseListObject
    public List<Course> getCourseList() {
        return courseList;
    }

}
