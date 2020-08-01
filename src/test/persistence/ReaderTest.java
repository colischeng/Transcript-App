package persistence;

import model.Course;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    @Test
    void testParseCoursesFile1() {
        try {
            List<Course> courses = Reader.readCourses(new File("./data/testCourseFile1.txt"));
            Course courseOne = courses.get(0);
            assertEquals(0, courseOne.getId());
            assertEquals("COMM", courseOne.getType());
            assertEquals(491, courseOne.getCode());
            assertEquals(85, courseOne.getGrade());
            assertEquals(3, courseOne.getCredits());

            Course courseTwo = courses.get(1);
            assertEquals(1, courseTwo.getId());
            assertEquals("CPSC", courseTwo.getType());
            assertEquals(110, courseTwo.getCode());
            assertEquals(90, courseTwo.getGrade());
            assertEquals(4, courseTwo.getCredits());

            // check that nextCourseId has been set correctly
            Course nextCourse = new Course("CPSC", 221, 81, 4);
            assertEquals(2, nextCourse.getId());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testParseCoursesFile2() {
        try {
            List<Course> courses = Reader.readCourses(new File("./data/testCourseFile2.txt"));
            Course courseOne = courses.get(0);
            assertEquals(2, courseOne.getId());
            assertEquals("EPSE", courseOne.getType());
            assertEquals(449, courseOne.getCode());
            assertEquals(90, courseOne.getGrade());
            assertEquals(3, courseOne.getCredits());

            Course courseTwo = courses.get(1);
            assertEquals(3, courseTwo.getId());
            assertEquals("CPSC", courseTwo.getType());
            assertEquals(121, courseTwo.getCode());
            assertEquals(80, courseTwo.getGrade());
            assertEquals(4, courseTwo.getCredits());

            // check that nextCourseId has been set correctly
            Course nextCourse = new Course("MATH", 180, 81, 3);
            assertEquals(4, nextCourse.getId());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readCourses(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
