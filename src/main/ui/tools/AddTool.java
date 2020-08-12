package ui.tools;

import model.Course;
import model.Transcript;

import javax.swing.*;

public class AddTool extends Tool {


    public AddTool(String label, String soundLocation, Transcript transcript, JTextArea textArea,
                   JTextField courseType, JTextField courseNumber, JTextField grade, JTextField credits,
                   JTextField target, JTextField remove) {
        super(label, soundLocation, transcript, textArea, courseType, courseNumber, grade, credits, target, remove);
    }

    @Override
    public void doAction(Transcript transcript, JTextArea textArea, JTextField courseType, JTextField courseNumber,
                         JTextField grade, JTextField credits, JTextField target, JTextField remove)  {
        String initialCourseType = "";
        int initialCourseNumber = 0;
        int initialCourseGrade = 0;
        int initialCourseCredits = 0;
        try {
            initialCourseType = (courseType.getText());
            initialCourseNumber = Integer.parseInt(courseNumber.getText());
            initialCourseGrade = Integer.parseInt(grade.getText());
            initialCourseCredits = Integer.parseInt(credits.getText());
        } catch (Exception exception) {
            textArea.setText(printTranscript() + "\nAn error has occurred and no courses were added.");
        }
        Course course = new Course(initialCourseType, initialCourseNumber, initialCourseGrade, initialCourseCredits);
        transcript.addCourse(course);
        textArea.setText(printTranscript()
                + "\n" + initialCourseType + " " + initialCourseNumber + " has been added to your transcript\n");
        System.out.println(initialCourseType + " " + initialCourseNumber + " has been added to your transcript\n");
    }

    //EFFECTS: creates the "Add a course" panel (with fields) for the GUI
    public JPanel createAddFields() {
        JPanel addCoursePanel = new JPanel();
        addCoursePanel.setLayout(new BoxLayout(addCoursePanel, BoxLayout.Y_AXIS));

        JPanel typePanel = questionPanels("What is the course type?", "");
        JPanel numberPanel = questionPanels("What is the course number?", "");
        JPanel gradePanel = questionPanels("What was your grade in the course?", "");
        JPanel creditPanel = questionPanels("How many credits was this course worth?", "");

        courseType = ((JTextField) typePanel.getComponent(1));
        courseNumber = ((JTextField) numberPanel.getComponent(1));
        grade = ((JTextField) gradePanel.getComponent(1));
        credits = ((JTextField) creditPanel.getComponent(1));

        AddTool addButton = new AddTool(this.label, this.soundLocation, this.transcript, this.textArea, this.courseType,
                this.courseNumber, this.grade, this.credits, this.target, this.remove);

        addCoursePanel.add(typePanel);
        addCoursePanel.add(numberPanel);
        addCoursePanel.add(gradePanel);
        addCoursePanel.add(creditPanel);
        addCoursePanel.add(addButton.getButton());
        return addCoursePanel;
    }
}
