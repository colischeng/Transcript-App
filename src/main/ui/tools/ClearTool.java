package ui.tools;

import model.Transcript;

import javax.swing.*;

public class ClearTool extends Tool {


    public ClearTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                     JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                     JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove)  {
        transcript.getCourseList().clear();
        textArea.setText("Your transcript history has been cleared");
        System.out.println("\nYour Transcript is now clear");
    }
}
