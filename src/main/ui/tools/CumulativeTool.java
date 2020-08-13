package ui.tools;

import model.Transcript;

import javax.swing.*;

//Create a panel for the GUI with  a button that can calculate the cumulative GPA of the transcript
public class CumulativeTool extends Tool {

    //EFFECTS: construct new CumulativeTool object
    public CumulativeTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                          JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                          JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    //MODIFIES: this
    //EFFECTS: Calculate the cumulative GPA on a transcript and then change the textField
    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        if (transcript.getCourseList().size() == 0) {
            textArea.setText("No courses are currently in your transcript. Cumulative GPA was not calculated");
            System.out.println("No courses are currently in your transcript. Cumulative GPA was not calculated");
        } else {
            textArea.setText(printTranscript() + "\nYour cumulative GPA is " + transcript.cumulativeGPA());
            System.out.println("Your cumulative GPA is " + transcript.cumulativeGPA());
        }
    }

}
