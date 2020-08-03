package persistence;

import model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest { // taken largely from TellerApp
    private static final String TEST_FILE = "./data/testCourses.txt";
    private Writer testWriter;
    private Course courseOne;
    private Course courseTwo;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        courseOne = new Course("ENGL",110,75,3);
        courseTwo = new Course("CPSC",448,87,6);
    }

    @Test
    void testWriteAccounts() {
        // save courses to file
        testWriter.write(courseOne);
        testWriter.write(courseTwo);
        testWriter.close();

        // now read them back in and verify that the courses have the expected values
        try {
            List<Course> courses = Reader.readCourses(new File(TEST_FILE));
            Course courseOne = courses.get(0);
            assertEquals(0, courseOne.getId());
            assertEquals("ENGL", courseOne.getType());
            assertEquals(110, courseOne.getCode());
            assertEquals(75, courseOne.getGrade());
            assertEquals(3, courseOne.getCredits());

            Course courseTwo = courses.get(1);
            assertEquals(1, courseTwo.getId());
            assertEquals("CPSC", courseTwo.getType());
            assertEquals(448, courseTwo.getCode());
            assertEquals(87, courseTwo.getGrade());
            assertEquals(6, courseTwo.getCredits());

            // verify that ID of next Course created is 3 (checks that nextCourseId was restored)
            Course next = new Course("COMM", 101, 80, 3);
            assertEquals(2, next.getId());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
