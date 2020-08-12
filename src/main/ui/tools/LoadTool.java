package ui.tools;

import model.Transcript;

import javax.swing.*;

public class LoadTool extends Tool {
    private static final String COURSES_FILE = "./data/courses.txt";

    public LoadTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                    JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                    JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        try {
            this.transcript.loadCourses();
            textArea.setText(printTranscript() + "\nCourses loaded from" + COURSES_FILE);
        } catch (Exception e) {
            textArea.setText("Unable to load transcript to " + COURSES_FILE);
        }

    }
}
