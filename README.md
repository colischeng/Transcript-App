# GPA Calculator Project

## Simple GPA Tracker and Calculator


A project designed as an aid to students to track their cumulative GPA and perform calculations that can help with 
future course planning. I thought this would be an interest project to undergo as the Grade Summary page on UBC's
student services centre misses many features.


## User Stories

- As a user, I want to be able to add a new course onto my transcript
- As a user, I want to be able to calculate the cumulative GPA on my transcript
- As a user, I want to be able to determine the grade in my next 3-credit course to achieve a target GPA
- As a user, I want to be able to print my entire transcript
- As a user, I want to be able to save my transcript to file
- As a user, I want to be able to reload my transcript and resume exactly from where I left off

## Instructions for Grader

- You can generate the first required event of adding a course to the transcript by entering the appropriate 
information in the "Add a Course" fields and clicking the button "Add a Course"
- You can generate the second required event of removing a course on the transcript by inputting an index within
range and clicking the button "Remove Course"
- You can trigger my audio component by clicking the "Adding a Course" button. The GUI should say the words
"Add a course".
- You can save the state of my application by clicking the "Save Transcript" button
- You can reload the state of my application by clicking the "Load Transcript" button

## Phase 4: Task 2

- *Option 1*: Test and design a class that is robust.  You must have at least one method that throws a checked 
exception. You must have one test for the case where the exception is expected and another where the exception is not 
expected.
    - In the **Transcript Class**, the method **target(int goal)** throws a checked exception called **Unattainable
     Exception** that is well tested in the **TranscriptTest Class** in **targetTestNoException()** and
     **targetTestThrowsException()**

 ## Phase 4: Task 3
 
 - *1. Cohesion Improvement*: Created the abstract class *Tool* which is extended by all the tools viewed in the GUI.
 Originally, all GUI components were housed in the *TranscriptApp* class leading to low readibility. Due to this 
 improvement, the number of lines of code in *TranscriptApp* was reduced from **511 to 261**   