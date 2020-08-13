package ui.tools;

import model.Course;
import ui.TranscriptApp;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

//An abstract class that is extended by all the panels (functions) of the GUI
public abstract class Tool {
    protected String label;
    protected String soundLocation;
    protected TranscriptApp transcriptApp;
    protected JTextArea textArea;
    protected JButton button;
    protected JTextField courseType;
    protected JTextField courseNumber;
    protected JTextField grade;
    protected JTextField credits;
    protected JTextField target;
    protected JTextField remove;

    //EFFECTS: construct new Tool object
    public Tool(String label, String soundLocation, TranscriptApp transcriptApp, JTextArea textArea, JTextField
            courseType, JTextField courseNumber, JTextField grade, JTextField credits, JTextField target, JTextField
            remove) {
        this.label = label;
        this.soundLocation = soundLocation;
        this.transcriptApp = transcriptApp;
        this.textArea = textArea;
        this.courseType = courseType;
        this.courseNumber = courseNumber;
        this.grade = grade;
        this.credits = credits;
        this.target = target;
        this.remove = remove;
        JButton button = new JButton(label);
        ActionListener actionListener = e -> { //taken from "Java Programming For Beginners" from Daniel Korig
            playSound(this.soundLocation);
            doAction(this.transcriptApp, this.textArea, this.courseType, this.courseNumber, this.grade, this.credits,
                    this.target, this.remove);
        };
        button.addActionListener(actionListener);
        this.button = button;
    }

    //EFFECTS: Abstraction function that represents the actions taken by a panel
    public abstract void doAction(TranscriptApp transcriptApp, JTextArea textArea, JTextField courseType,
                                  JTextField courseNumber, JTextField grade, JTextField credits, JTextField target,
                                  JTextField remove);

    //EFFECTS: Adds the audio component of the GUI. Plays a .wav file depending on the button pushed
    //taken from http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    //EFFECTS: return button field
    public JButton getButton() {
        return this.button;
    }

    //EFFECTS: Produces the string that is representative of the entire transcript object
    String printTranscript() {
        int index = 1;
        String title = ("Your Transcript \n\n");
        String records = "";
        for (Course c : this.transcriptApp.getTranscript().getCourseList()) {
            String indexString = (index + ". ");
            records = (records + indexString + c.toString() + "\n");
            index++;
        }
        return (title + records);
    }

    //EFFECTS: Creates panels that have editable text fields (for the Add, Target, Remove functionalities)
    public JPanel questionPanels(String label, String question) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel prompt = new JLabel(label);
        JTextField field = new JTextField(question);
        field.setPreferredSize(new Dimension(10, 22));

        panel.add(prompt,0);
        panel.add(field,1);
        return panel;
    }

}
