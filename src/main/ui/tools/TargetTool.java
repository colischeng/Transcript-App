package ui.tools;

import exceptions.UnattainableException;
import model.Transcript;

import javax.swing.*;

public class TargetTool extends Tool {
    public TargetTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                      JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                      JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

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
