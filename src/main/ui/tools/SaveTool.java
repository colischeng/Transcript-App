package ui.tools;

import model.Course;
import persistence.Writer;
import ui.TranscriptApp;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

//Create a panel for the GUI with  a button that can save a transcript object
public class SaveTool extends Tool {

    //EFFECTS: construct new SaveTool object
    private static final String COURSES_FILE = "./data/courses.txt";

    public SaveTool(String label, String soundLocation, TranscriptApp transcriptApp, JTextArea textArea,
                    JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                    JTextField target, JTextField remove) {
        super(label, soundLocation, transcriptApp, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    //MODIFIES: this
    //EFFECTS: Save a transcript object and then change the textField
    @Override
    public void doAction(TranscriptApp transcriptApp, JTextArea textArea, JTextField courseType, JTextField
            courseNumber, JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        try {
            saveCourses();
            textArea.setText(printTranscript() + "\nCourses saved to file" + COURSES_FILE);
        } catch (Exception e) {
            textArea.setText("Unable to save transcript to " + COURSES_FILE);
        }
    }

    // EFFECTS: saves state of transcripts to COURSES_FILE
    public void saveCourses() {
        try {
            Writer writer = new Writer(new File(COURSES_FILE));
            for (Course c : transcriptApp.getTranscript().getCourseList()) {
                writer.write(c);
            }
            writer.close();
            System.out.println("Courses saved to file " + COURSES_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save transcript to " + COURSES_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }
}
