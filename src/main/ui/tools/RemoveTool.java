package ui.tools;

import model.Transcript;

import javax.swing.*;

public class RemoveTool extends Tool {


    public RemoveTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                      JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                      JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove) {
        String response = remove.getText();
        int removeField;
        try {
            removeField = Integer.parseInt(response);
            transcript.getCourseList().remove(removeField - 1);
            textArea.setText(
                    printTranscript() + "\nThe course at index " + removeField + " was removed");
        } catch (Exception exception) {
            textArea.setText(printTranscript()
                    + "\nAn error occurred and no courses were removed. Check that your index is within range.");
        }
    }

    public JPanel createRemovalFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel remove = questionPanels("Which nth course on your transcript do you want to remove?", "");

        this.remove = ((JTextField) remove.getComponent(1));

        RemoveTool removeButton = new RemoveTool(this.label, this.soundLocation, this.transcript, this.textArea,
                this.courseType, this.courseNumber, this.grade, this.credits, this.target, this.remove);

        panel.add(remove);
        panel.add(removeButton.getButton());
        return panel;
    }
}
