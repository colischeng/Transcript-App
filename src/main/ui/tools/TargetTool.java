package ui.tools;

import exceptions.UnattainableException;
import model.Transcript;

import javax.swing.*;

//Create a panel for the GUI with fields and a button that can calculate a target GPA
public class TargetTool extends Tool {

    //EFFECTS: construct new TargetTool object
    public TargetTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                      JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                      JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    //MODIFIES: this
    //EFFECTS: Calculate a target GPA based on the fields that have been filled in by the use and then change the
    // textField
    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        String response = target.getText();
        int targetField = 0;
        try {
            targetField = Integer.parseInt(response);
        } catch (Exception exception) {
            //initialize
        }

        try {
            transcript.target(targetField);
            textArea.setText(
                    printTranscript() + "\nYou need to score " + transcript.target(targetField)
                            + " in your next 3-credit course.");
        } catch (UnattainableException unattainableException) {
            textArea.setText(printTranscript() + "\nThis GPA is not attainable.");
        }
    }

    //EFFECTS: creates the "Calculate Target" panel (with fields) for the GUI
    public JPanel createTargetFields() {
        JPanel targetPanel = new JPanel();
        targetPanel.setLayout(new BoxLayout(targetPanel, BoxLayout.Y_AXIS));

        JPanel questionPanel =
                questionPanels("What GPA do you want to achieve with your next 3-credit course?", "");

        target = ((JTextField) questionPanel.getComponent(1));

        TargetTool targetButton = new TargetTool(this.label, this.soundLocation, this.transcript, this.textArea,
                this.courseType, this.courseNumber, this.grade, this.credits, this.target, this.remove);

        targetPanel.add(questionPanel);
        targetPanel.add(targetButton.getButton());
        return targetPanel;
    }
}
