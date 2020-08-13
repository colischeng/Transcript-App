package ui.tools;

import model.Course;
import model.Transcript;
import persistence.Reader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

//Create a panel for the GUI with a button that can load a transcript
public class LoadTool extends Tool {
    private static final String COURSES_FILE = "./data/courses.txt";

    //EFFECTS: construct new LoadTool object
    public LoadTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                    JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                    JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    //MODIFIES: this
    //EFFECTS: Load a previously saved transcript and then change the textField
    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        try {
            loadCourses();
            textArea.setText(printTranscript() + "\nCourses loaded from" + COURSES_FILE);
        } catch (Exception e) {
            textArea.setText("Unable to load transcript to " + COURSES_FILE);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads courses from COURSES_FILE, if that file exists;
    // otherwise initializes transcript with empty transcript
    private void loadCourses() {
        try {
            List<Course> courses = Reader.readCourses(new File(COURSES_FILE));
            if (courses.size() == 0) {
                System.out.println("No past transcript exists. No courses have been loaded.");
                textArea.setText("No past transcript exists. No courses have been loaded.");
            } else {
                transcript.getCourseList().clear();
                for (Course c : courses) {
                    transcript.addCourse(c);
                }
                System.out.println("Courses loaded from " + COURSES_FILE + "\n");
                int index = 1;
                System.out.println("Your Transcript \n");
                for (Course c : transcript.getCourseList()) {
                    String indexString = (index + ". ");
                    System.out.println(indexString + c.toString());
                    index++;
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to load transcript to " + COURSES_FILE);
        }
    }
}
