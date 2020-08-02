package persistence;

import model.Course;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read transcript data from a file
// taken largely from TellerApp
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: constructs a default constructor that will read the data file
    public Reader(){}

    // EFFECTS: returns a list of courses parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Course> readCourses(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of courses parsed from list of strings
    // where each string contains data for one course
    private static List<Course> parseContent(List<String> fileContent) {
        List<Course> courses = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            courses.add(parseCourse(lineComponents));
        }

        return courses;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 6 where element 0 represents the
    // id of the next course to be constructed, element 1 represents
    // the id, element 2 represents the type, element 3 represents
    // the code, element 4 represents the grade and element 5
    // represents the credits of the course to be constructed
    // EFFECTS: returns a course constructed from components
    private static Course parseCourse(List<String> components) {
        int nextId = Integer.parseInt(components.get(0));
        int id = Integer.parseInt(components.get(1));
        String type = components.get(2);
        int code = Integer.parseInt(components.get(3));
        int grade = Integer.parseInt(components.get(4));
        int credit = Integer.parseInt(components.get(5));
        return new Course(nextId, id, type, code, grade, credit);
    }
}
