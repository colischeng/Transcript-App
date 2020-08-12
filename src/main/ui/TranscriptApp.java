package ui;

import exceptions.UnattainableException;
import model.Course;
import model.Transcript;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

// Represents the instantiated transcript app class

public class TranscriptApp extends JFrame {
    private static final String COURSES_FILE = "./data/courses.txt";
    private Transcript transcript;
    private Scanner input;
    private JTextArea textArea;

    private JTextField textFieldCourseType;
    private JTextField textFieldCourseNumber;
    private JTextField textFieldGrade;
    private JTextField textFieldCredits;
    private JTextField textFieldTarget;
    private JTextField textFieldRemove;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

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

        transcript = new Transcript();
        textArea = createTextField();
        textFieldCourseType = null;
        textFieldCourseNumber = null;
        textFieldGrade = null;
        textFieldCredits = null;
        textFieldTarget = null;
        textFieldRemove = null;

        panel.add(methodsPanel());
        panel.add(textArea);

        add(panel);
        setVisible(true);
    }

    //EFFECTS: adds the "Add A Course", "Calculate Cumulative", "Calculate Target", "Remove Course", "Clear Transcript",
    // "Save Transcript", "Load Transcript", panels/buttons onto the GUI
    private JPanel methodsPanel() {
        JPanel mainPanel = new JPanel();
        setLayout(new FlowLayout());
        AddTool addButton = new AddTool("Add A Course Transcript","./data/audio/addACourse.wav",
                this.transcript, this.textArea, textFieldCourseType, textFieldCourseNumber, textFieldGrade,
                textFieldCredits, null,null);
        mainPanel.add(addButton.createAddFields());
        mainPanel.add(oneFieldPanel());
        mainPanel.add(buttonsOnlyPanel());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return mainPanel;
    }

    private JPanel oneFieldPanel() {
        JPanel oneFieldPanel = new JPanel();
        TargetTool targetButton = new TargetTool("Calculate target GPA",
                "./data/audio/calculateTargetGPA.wav", this.transcript, this.textArea,null,
                null,null,null,this.textFieldTarget,null);
        RemoveTool removeButton = new RemoveTool("Remove a course","./data/audio/removeCourse.wav",
                this.transcript, this.textArea,null,null,null,null,
                null, this.textFieldRemove);
        oneFieldPanel.setLayout(new BoxLayout(oneFieldPanel, BoxLayout.Y_AXIS));
        oneFieldPanel.add(targetButton.createTargetFields());
        oneFieldPanel.add(removeButton.createRemovalFields());
        return oneFieldPanel;
    }

    private JPanel buttonsOnlyPanel() {
        JPanel buttonsOnlyPanel = new JPanel();
        CumulativeTool cumulativeButton = new CumulativeTool("Calculate Cumulative",
                "./data/audio/calculateCumulative.wav", this.transcript, this.textArea, null,
                null, null,null,null,null);
        ClearTool clearButton = new ClearTool("Clear View","./data/audio/clearTranscript.wav",
                this.transcript, this.textArea,null,null,null,null, null,
                null);
        SaveTool saveButton = new SaveTool("Save Transcript","./data/audio/saveTranscript.wav",
                this.transcript, this.textArea,null,null,null,null, null,
                null);
        LoadTool loadButton = new LoadTool("Load Transcript","./data/audio/loadTranscript.wav",
                this.transcript, this.textArea,null,null,null,null, null,
                null);
        buttonsOnlyPanel.setLayout(new BoxLayout(buttonsOnlyPanel, BoxLayout.Y_AXIS));
        buttonsOnlyPanel.add(cumulativeButton.getButton());
        buttonsOnlyPanel.add(clearButton.getButton());
        buttonsOnlyPanel.add(saveButton.getButton());
        buttonsOnlyPanel.add(loadButton.getButton());
        return buttonsOnlyPanel;
    }


    //EFFECTS: Places the transcript area onto the GUI (the part of the GUI that, according to the project
    // specifications, "displays the Xs that have been added to the Y")
    public JTextArea createTextField() {
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension((WIDTH / 2), (int) (HEIGHT / 1.5)));
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
            this.transcript.saveCourses();
        } else if (command.equals("l")) {
            this.transcript.loadCourses();
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
        try {
            transcript.target(goal);
            System.out.println("\nYou need to score " + transcript.target(goal) + " in your next 3-credit course.");
        } catch (UnattainableException e) {
            System.out.println("\nThis GPA is not attainable.");
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