package ui.tools;

import model.Transcript;

import javax.swing.*;
import java.io.FileNotFoundException;

public class SaveTool extends Tool {
    private static final String COURSES_FILE = "./data/courses.txt";

    public SaveTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                    JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                    JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        try {
            this.transcript.saveCourses();
            textArea.setText(printTranscript() + "\nCourses saved to file" + COURSES_FILE);
        } catch (Exception e) {
            textArea.setText("Unable to save transcript to " + COURSES_FILE);
        }
    }
}
