package ui.tools;

import model.Transcript;

import javax.swing.*;

//Create a panel for the GUI with a button that can clear all the courses in a transcript
public class ClearTool extends Tool {

    //EFFECTS: construct new AddTool object
    public ClearTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                     JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                     JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    //MODIFIES: this
    //EFFECTS: Removes all the courses in the transcript object and then change the textField
    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove)  {
        transcript.getCourseList().clear();
        textArea.setText("Your transcript history has been cleared");
        System.out.println("\nYour Transcript is now clear");
    }
}
