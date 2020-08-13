package ui.tools;

import model.Transcript;

import javax.swing.*;

//Create a panel for the GUI with fields and a button that can remove a course from the transcript
public class RemoveTool extends Tool {

    //EFFECTS: construct new RemoveTool object
    public RemoveTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                      JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                      JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    //MODIFIES: this
    //EFFECTS: Remove a course from the transcript object based on the index that has been filled in by the use and
    // then change the textField
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

    //EFFECTS: creates the "Remove a course" panel (with fields) for the GUI
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
