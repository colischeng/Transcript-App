package ui;

import model.Course;
import model.Transcript;

import java.util.Scanner;

public class TranscriptApp {
    private Transcript transcript;
    private Scanner input;

    //EFFECTS: runs transcript application
    public TranscriptApp() {
        runTranscript();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTranscript() {   // taken from the runTeller method of the TellerApp
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);
        transcript = new Transcript();

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
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a course");
        System.out.println("\tc -> display cumulative GPA");
        System.out.println("\tt -> calculate target GPA");
        System.out.println("\tr -> remove last course");
        System.out.println("\tp -> print transcript");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("c")) {
            doCumulative();
        } else if (command.equals("t")) {
            doTarget();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("p")) {
            doPrint();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a course
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

    // EFFECTS: calculates cumulative GPA
    private void doCumulative() {
        System.out.println("Your cumulative GPA is " + transcript.cumulativeGPA());

    }


    // EFFECTS: performs target calculation
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
            transcript.removeCourse();
            System.out.println("Last added course was successfully removed.");
        } else {
            System.out.println("No courses can be removed from an empty transcript");
        }
    }

    // EFFECTS: print the entire transcript object
    private void doPrint() {
        System.out.println("Your Transcript \n");
        for (Course c : transcript.getCourseList()) {
            System.out.println(c.toString());
        }
    }

}