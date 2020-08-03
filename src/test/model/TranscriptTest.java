package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranscriptTest {
    Course cpsc110;
    Course cpsc221;
    Course math221;
    Course comm491;
    Course math307;
    Course cpsc121;

    Transcript testTranscript;

    @BeforeEach
    void runBefore() {
        cpsc110 = new Course("CPSC", 110, 90, 4);
        cpsc221 = new Course("CPSC", 221, 85, 4);
        math221 = new Course("MATH", 221, 90, 3);
        comm491 = new Course("COMM", 491, 85, 3);
        math307 = new Course("MATH", 307, 74, 3);
        cpsc121 = new Course("CPSC", 121, 81, 4);
        testTranscript = new Transcript();
        testTranscript.addCourse(cpsc110);
        testTranscript.addCourse(cpsc221);
        testTranscript.addCourse(math221);
        testTranscript.addCourse(comm491);
        testTranscript.addCourse(math307);
        testTranscript.addCourse(cpsc121);
    }

    @Test
    public void addCourseTest() {
        Course cpsc210 = new Course("CPSC", 210, 91, 4);
        testTranscript.addCourse(cpsc210);
        assertEquals(7, testTranscript.length());
    }

    @Test
    public void cumulativeGPATest() {
        assertEquals(84, testTranscript.cumulativeGPA());
    }

    @Test
    public void targetTest() {
        assertEquals(-1, testTranscript.target(100));
        assertEquals(-1, testTranscript.target(100));
        assertEquals(89, testTranscript.target(85));
        assertEquals(41, testTranscript.target(79));
        assertEquals(-1, testTranscript.target(0));
    }

    @Test
    public void removeCoursesTest() {
        testTranscript.removeCourse(5);
        assertEquals(cpsc110,testTranscript.getCourseList().get(0));
        assertEquals(cpsc221,testTranscript.getCourseList().get(1));
        assertEquals(math221,testTranscript.getCourseList().get(2));
        assertEquals(comm491,testTranscript.getCourseList().get(3));
        assertEquals(cpsc121,testTranscript.getCourseList().get(4));
        assertEquals(5, testTranscript.length());

    }

    @Test
    public void lengthTest() {
        assertEquals(6, testTranscript.length());
    }

    @Test
    public void getCourseListTest() {
        assertEquals(cpsc110, testTranscript.getCourseList().get(0));
        assertEquals(cpsc221, testTranscript.getCourseList().get(1));
        assertEquals(math221, testTranscript.getCourseList().get(2));
        assertEquals(6, testTranscript.length());
    }

}