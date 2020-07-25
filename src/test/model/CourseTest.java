package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {
    Course testCourse;

    @BeforeEach
    void runBefore(){
        testCourse = new Course("COMM", 491, 95, 4);
    }


    @Test
    public void toStringTest() {
        assertEquals ("Type: COMM || Code: 491 || Grade: 95 || Credits: 4", testCourse.toString());
    }
}
