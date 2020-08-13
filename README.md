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
    - In the **Transcript**  Class, the method **target(int goal)** throws a checked exception called 
    **UnattainableException** that is well tested in the **TranscriptTest** Class through **targetTestNoException()**,
     **targetTestThrowsExceptionOverOneHundred**, and **targetTestThrowsExceptionLessThanZero** in the
      **TranscriptTest** class

 ## Phase 4: Task 3

- All problems: Adding a course, clearing a transcript, calculating the target GPA, loading a transcript, removing a 
course, saving a transcript, and calculating a target grade are not functions that should be implemented in the
same class that handles the GUI. Thus, the *TranscriptApp* class exhibited very low cohesion. It would make sense
to move the logic of these tools into a different class. These panels all share some commonality, so abstraction could
 be used.
- *1. Cohesion Improvement*: Removed the methods *createAddFields()* and  *createAddButton()* in the *TranscriptApp*
  class (along with their accompanying fields) and created the class *AddTool* that extends the abstract class *Tool* 
  that handles all the functionality of adding courses in the GUI
- *2. Cohesion Improvement*: Removed the method *createCumulativeButton()* in the *TranscriptApp* class and created the
 class *CumulativeTool* that extends the abstract class *Tool* that handles all the functionality of calculating the
  cumulative GPA in the GUI
