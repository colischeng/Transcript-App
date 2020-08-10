package ui;

import model.Course;
import model.Transcript;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// Represents the instantiated transcript app class

public class TranscriptApp extends JFrame {
    private static final String COURSES_FILE = "./data/courses.txt";
    private Transcript transcript = new Transcript();
    private Scanner input;
    private JTextArea textArea;

    private JTextField textFieldCourseType;
    private JTextField textFieldCourseNumber;
    private JTextField textFieldGrade;
    private JTextField textFieldCredits;
    private JTextField textFieldTarget;
    private JTextField textFieldRemove;

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    //EFFECTS: runs transcript application
    public TranscriptApp() {
        super("Transcript App");
        initializeGraphics();
        runTranscript();
    }

    //EFFECTS: initializes the GUI associated with the transcript app
    private void initializeGraphics() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        panel.add(methodsPanel());

        textArea = createTextField();

        panel.add(textArea);

        add(panel);
        setVisible(true);
    }

    //EFFECTS: adds the "Add A Course", "Calculate Cumulative", "Calculate Target", "Remove Course", "Clear Transcript",
    // "Save Transcript", "Load Transcript", panels/buttons onto the GUI
    private JPanel methodsPanel() {
        JPanel panel = new JPanel();
        setLayout(new FlowLayout());

        panel.add(createAddFields());
        panel.add(createCumulativeButton());
        panel.add(createTargetFields());
        panel.add(createRemovalFields());
        panel.add(createClearButton());
        panel.add(createSaveButton());
        panel.add(createLoadButton());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return panel;
    }

    //EFFECTS: creates the "Add a course" panel (with fields) for the GUI
    private JPanel createAddFields() {
        JPanel addCoursePanel = new JPanel();
        addCoursePanel.setLayout(new BoxLayout(addCoursePanel, BoxLayout.Y_AXIS));

        JPanel typePanel = questionPanels("What is the course type?", "");
        JPanel numberPanel = questionPanels("What is the course number?", "");
        JPanel gradePanel = questionPanels("What was your grade in the course?", "");
        JPanel creditPanel = questionPanels("How many credits was this course worth?", "");

        textFieldCourseType = ((JTextField) typePanel.getComponent(1));
        textFieldCourseNumber = ((JTextField) numberPanel.getComponent(1));
        textFieldGrade = ((JTextField) gradePanel.getComponent(1));
        textFieldCredits = ((JTextField) creditPanel.getComponent(1));

        JButton addButton = createAddButton();

        addCoursePanel.add(typePanel);
        addCoursePanel.add(numberPanel);
        addCoursePanel.add(gradePanel);
        addCoursePanel.add(creditPanel);
        addCoursePanel.add(addButton);
        return addCoursePanel;
    }

    //MODIFIES: this
    //EFFECTS: creates the "Add a course" button for the "Add a course" panel for the GUI
    public JButton createAddButton() {
        JButton addButton = new JButton("Add a Course");
        ActionListener actionListener = e -> {
            playSound("./data/audio/addACourse.wav");
            String courseType = "";
            int courseNumber = 0;
            int courseGrade = 0;
            int courseCredits = 0;
            try {
                courseType = (textFieldCourseType.getText());
                courseNumber = Integer.parseInt(textFieldCourseNumber.getText());
                courseGrade = Integer.parseInt(textFieldGrade.getText());
                courseCredits = Integer.parseInt(textFieldCredits.getText());
            } catch (Exception exception) {
                textArea.setText(printTranscript() + "\nAn error has occurred and no courses were added.");
            }
            Course course = new Course(courseType, courseNumber, courseGrade, courseCredits);
            transcript.addCourse(course);
            textArea.setText(printTranscript()
                    + "\n" + courseType + " " + courseNumber + " has been added to your transcript\n");
            System.out.println(courseType + " " + courseNumber + " has been added to your transcript\n");
        };
        addButton.addActionListener(actionListener);
        return addButton;
    }

    //EFFECTS: creates the "Calculate Target" panel (with fields) for the GUI
    private JPanel createTargetFields() {
        JPanel targetPanel = new JPanel();
        targetPanel.setLayout(new BoxLayout(targetPanel, BoxLayout.Y_AXIS));

        JPanel questionPanel =
                questionPanels("What GPA do you want to achieve with your next 3-credit course?", "");

        textFieldTarget = ((JTextField) questionPanel.getComponent(1));

        JButton targetButton = createTargetButton();

        targetPanel.add(questionPanel);
        targetPanel.add(targetButton);
        return targetPanel;
    }

    //EFFECTS: creates the "Calculate target" button for the "Calculate target" panel for the GUI
    private JButton createTargetButton() {
        JButton targetButton = new JButton("Calculate Target GPA");
        ActionListener actionListener = e -> {
            playSound("./data/audio/calculateTargetGPA.wav");
            String response = textFieldTarget.getText();
            int targetField = 0;
            try {
                targetField = Integer.parseInt(response);
            } catch (Exception exception) {
                //initialize
            }
            if (transcript.target(targetField) == -1) {
                textArea.setText(printTranscript() + "\nThis GPA is not attainable.");
            } else {
                textArea.setText(
                        printTranscript() + "\nYou need to score " + transcript.target(targetField)
                                + " in your next 3-credit course.");
            }
        };
        targetButton.addActionListener(actionListener);
        return targetButton;
    }

    //MODIFIES: this
    //EFFECTS: creates the "Remove course" panel (with fields) for the GUI
    private JPanel createRemovalFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel remove = questionPanels("Which nth course on your transcript do you want to remove?", "");

        textFieldRemove = ((JTextField) remove.getComponent(1));

        JButton removeButton = createRemoveButton();

        panel.add(remove);
        panel.add(removeButton);
        return panel;
    }

    //MODIFIES: this
    //EFFECTS: creates the "Remove course" button for the "Remove course" panel for the GUI
    public JButton createRemoveButton() {
        JButton removeButton = new JButton("Remove Course");
        ActionListener actionListener = e -> {
            playSound("./data/audio/removeCourse.wav");
            String response = textFieldRemove.getText();
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


        };
        removeButton.addActionListener(actionListener);
        return removeButton;
    }

    //EFFECTS: Abstract method that creates panels that have editable text fields (Add, Target, Remove functionalities)
    public JPanel questionPanels(String label, String question) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel prompt = new JLabel(label);
        JTextField field = new JTextField(question);
        field.setPreferredSize(new Dimension(20, 22));

        panel.add(prompt,0);
        panel.add(field,1);
        return panel;
    }

    //EFFECTS: Places the transcript area onto the GUI (the part of the GUI that, according to the project
    // specifications, "displays the Xs that have been added to the Y")
    public JTextArea createTextField() {
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT / 1.5)));
        area.setEditable(false);
        area.setText(printTranscript());

        return area;
    }

    //EFFECTS: Produces the string that is representative of the entire transcript object
    private String printTranscript() {
        int index = 1;
        String title = ("Your Transcript \n\n");
        String records = "";
        for (Course c : transcript.getCourseList()) {
            String indexString = (index + ". ");
            records = (records + indexString + c.toString() + "\n");
            index++;
        }
        return (title + records);
    }

    //MODIFIES: this
    //EFFECTS: creates the "Calculate Cumulative" button for the GUI
    public JButton createCumulativeButton() {
        JButton cumulativeButton = new JButton("Calculate Cumulative");
        ActionListener actionListener = e -> { //taken from "Java Programming For Beginners" from Daniel Korig
            playSound("./data/audio/calculateCumulative.wav");
            if (transcript.getCourseList().size() == 0) {
                textArea.setText("No courses are currently in your transcript. Cumulative GPA was not calculated");
            } else {
                textArea.setText(printTranscript() + "\nYour cumulative GPA is " + transcript.cumulativeGPA());
            }
        };
        cumulativeButton.addActionListener(actionListener);
        return cumulativeButton;
    }

    //MODIFIES: this
    //EFFECTS: creates the "Clear Transcript" button for the GUI
    public JButton createClearButton() {
        JButton clearButton = new JButton("Clear Transcript");
        ActionListener actionListener = e -> { //taken from "Java Programming For Beginners" from Daniel Korig
            playSound("./data/audio/clearTranscript.wav");
            transcript.getCourseList().clear();
            textArea.setText("Your transcript history has been cleared");
            System.out.println("\nYour Transcript is now clear");
        };
        clearButton.addActionListener(actionListener);
        return clearButton;
    }

    //MODIFIES: this
    //EFFECTS: creates the "Save Transcript" button for the GUI
    public JButton createSaveButton() {
        JButton saveButton = new JButton("Save Transcript");
        ActionListener actionListener = e -> {
            playSound("./data/audio/saveTranscript.wav");
            saveCourses();
        };
        saveButton.addActionListener(actionListener);
        return saveButton;
    }

    // MODIFIES: this
    //EFFECTS: creates the "Load Transcript" button for the GUI
    public JButton createLoadButton() {
        JButton loadButton = new JButton("Load Transcript");
        ActionListener actionListener = e -> {
            playSound("./data/audio/loadTranscript.wav");
            loadCourses();
        };
        loadButton.addActionListener(actionListener);
        return loadButton;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTranscript() {   // taken from the runTeller method of the TellerApp
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: loads courses from COURSES_FILE, if that file exists;
    // otherwise initializes transcript with empty transcript
    private void loadCourses() {
        try {
            List<Course> courses = Reader.readCourses(new File(COURSES_FILE));
            if (courses.size() == 0) {
                System.out.println("No past transcript exists. No courses have been loaded.");
            } else {
                transcript.getCourseList().clear();
                for (Course c : courses) {
                    transcript.addCourse(c);
                }
                System.out.println("Courses loaded from " + COURSES_FILE + "\n");
                doPrint();
                createTextField();
            }
            if (courses.size() == 0) {
                textArea.setText("No past transcript exists. No courses have been loaded.");
            } else {
                textArea.setText(printTranscript() + "\nCourses loaded from " + COURSES_FILE + "\n");
            }
        } catch (IOException e) {
            textArea.setText("Unable to load transcript to " + COURSES_FILE);
            init();
        }
    }

    // EFFECTS: saves state of transcripts to COURSES_FILE
    private void saveCourses() {
        try {
            Writer writer = new Writer(new File(COURSES_FILE));
            for (Course c : transcript.getCourseList()) {
                writer.write(c);
            }
            writer.close();
            textArea.setText(printTranscript() + "\nCourses saved to file" + COURSES_FILE);
            System.out.println("Courses saved to file " + COURSES_FILE);
        } catch (FileNotFoundException e) {
            textArea.setText("Unable to save transcript to " + COURSES_FILE);
            System.out.println("Unable to save transcript to " + COURSES_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes transcript
    private void init() {
        transcript = new Transcript();
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {   // similarly adapted from the TellerApp
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a course");
        System.out.println("\tc -> display cumulative GPA");
        System.out.println("\tt -> calculate target GPA");
        System.out.println("\tr -> remove last course");
        System.out.println("\ts -> save current transcript to file");
        System.out.println("\tl -> load existing transcript on file");
        System.out.println("\tp -> print transcript");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) { // similarly adapted from the TellerApp
            doAdd();
        } else if (command.equals("c")) {
            doCumulative();
        } else if (command.equals("t")) {
            doTarget();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("s")) {
            saveCourses();
        } else if (command.equals("l")) {
            loadCourses();
        } else if (command.equals("p")) {
            doPrint();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a course and adds it into the TranscriptApp
    private void doAdd() {
        System.out.println("\nWhat is the course type?");
        String type = input.next();
        System.out.println("\nWhat is the course number?");
        int code = input.nextInt();
        System.out.println("\nWhat was your grade in the course?");
        int grade = input.nextInt();
        System.out.println("\nHow many credits was this course worth?");
        int credit = input.nextInt();
        Course course = new Course(type, code, grade, credit);
        transcript.addCourse(course);
        System.out.println(type + " " + code + " has been added to your transcript\n");
    }

    // EFFECTS: display cumulative GPA
    private void doCumulative() {
        System.out.println("Your cumulative GPA is " + transcript.cumulativeGPA());

    }


    // EFFECTS: displays target calculation
    private void doTarget() {
        System.out.println("\nWhat GPA do you want to achieve through your next 3-credit course?");
        int goal = input.nextInt();
        if (transcript.target(goal) == -1) {
            System.out.println("\nThis GPA is not attainable.");
        } else {
            System.out.println("\nYou need to score " + transcript.target(goal) + " in your next 3-credit course.");
        }

    }

    // MODIFIES: this
    // EFFECTS: if transcript has at least one course
    //          - remove last course added to transcript
    //          - otherwise, do nothing
    private void doRemove() {
        if (transcript.length() > 0) {
            System.out.println("\nWhich nth course do you want to remove?\n");
            doPrint();
            int n = input.nextInt();
            transcript.removeCourse(n);
            System.out.println("Course was successfully removed.\n");
            doPrint();
        } else {
            System.out.println("No courses can be removed from an empty transcript");
        }
    }

    // EFFECTS: print the entire transcript object
    private void doPrint() {
        if (transcript.getCourseList().size() == 0) {
            System.out.println("Your Transcript is empty");
        } else {
            int index = 1;
            System.out.println("Your Transcript \n");
            for (Course c : transcript.getCourseList()) {
                String indexString = (index + ". ");
                System.out.println(indexString + c.toString());
                index++;
            }
        }
    }

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
}