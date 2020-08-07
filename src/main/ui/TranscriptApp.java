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

// Represents the instantiated transcript app class

public class TranscriptApp extends JFrame {
    private static final String COURSES_FILE = "./data/courses.txt";
    private Transcript transcript;
    private Scanner input;
    private JTextArea textArea;

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    //EFFECTS: runs transcript application
    public TranscriptApp() {
        super("Transcript App");
        transcript = new Transcript();
        initializeGraphics();
        runTranscript();
    }

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

    private JPanel methodsPanel() {
        JPanel panel = new JPanel();
        setLayout(new FlowLayout());

        panel.add(createAddFields());
        panel.add(createTargetFields());
        panel.add(createRemovalFields());
        panel.add(createClearButton());
        panel.add(createSaveButton());
        panel.add(createLoadButton());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return panel;
    }

    private JPanel createAddFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel typePanel = questionPanels("What is the course type?", "");
        JPanel numberPanel = questionPanels("What is the course number?", "");
        JPanel gradePanel = questionPanels("What was your grade in the course?", "");
        JPanel creditPanel = questionPanels("How many credits was this course worth?", "");

        JButton addButton = new JButton("Add a Course");

        panel.add(typePanel);
        panel.add(numberPanel);
        panel.add(gradePanel);
        panel.add(creditPanel);

        typePanel.getComponents();

        panel.add(addButton);

        return panel;
    }

    private JPanel createTargetFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel questionPanel =
                questionPanels("What GPA do you want to achieve with your next 3-credit course?", "");

        JButton targetButton = createTargetButton();

        panel.add(questionPanel);
        panel.add(targetButton);
        return panel;
    }

    public JButton createTargetButton() {
        JButton clearButton = new JButton("Calculate Target GPA");
        ActionListener actionListener = e -> {
            textArea.setText(printTranscript() + "\nYou need to achieve ___ in your next 3-credit course");
            doTarget();
        };
        clearButton.addActionListener(actionListener);
        return clearButton;
    }

    private JPanel createRemovalFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel field1 = questionPanels("Which nth course on your transcript do you want to remove?", "");

        JButton targetButton = new JButton("Remove Course");

        panel.add(field1);
        panel.add(targetButton);
        return panel;
    }

    public JPanel questionPanels(String label, String question) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel prompt = new JLabel(label);
        JTextField field = new JTextField(question);
        field.setPreferredSize(new Dimension(20, 22));

        panel.add(prompt);
        panel.add(field);
        return panel;
    }

    public JTextArea createTextField() {
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT / 1.5)));
        area.setEditable(false);
        area.setText(printTranscript());

        return area;
    }

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


    public JButton createClearButton() {
        JButton clearButton = new JButton("Clear Transcript");
        ActionListener actionListener = e -> { //taken from "Java Programming For Beginners" from Daniel Korig
            transcript.getCourseList().clear();
            textArea.setText("Your transcript history has been cleared");
            System.out.println("\nYour Transcript is now clear");
        };
        clearButton.addActionListener(actionListener);
        return clearButton;
    }

    public JButton createSaveButton() {
        JButton saveButton = new JButton("Save Transcript");
        ActionListener actionListener = e -> saveCourses();
        saveButton.addActionListener(actionListener);
        return saveButton;
    }

    public JButton createLoadButton() {
        JButton loadButton = new JButton("Load Transcript");
        ActionListener actionListener = e -> loadCourses();
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
                textArea.setText(printTranscript());
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
}