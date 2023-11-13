package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void getCourseName() {
        //test of de naam van de cursus wordt teruggegeven
        Course course = new Course("Java", "Beginner", new User());
        assertEquals("Java", course.getCourseName());
    }

    @Test
    void getCoordinator() {
        //test of de coordinator van de cursus wordt teruggegeven
        User coordinator = new User("Jan", "Janssen", " ", " ", "coördinator", "");
        Course course = new Course("Java", "Beginner", coordinator);
        assertEquals(coordinator, course.getCoordinator());
    }

    @Test
    void testEmptyConstructor() {
        //test of de constructor zonder parameters werkt
        Course course = new Course();
        assertEquals("Onbekend", course.getCourseName());
        assertEquals("Onbekend", course.getLevel());
        assertEquals("Onbekend", course.getCoordinator().getFirstName());

        int expectedId = 0;
        String expectedName = "Onbekend";
        String expectedLevel = "Onbekend";
        User expectedCoordinator = new User();

        assertEquals(expectedId, course.getCourseId());
        assertEquals(expectedName, course.getCourseName());
        assertEquals(expectedLevel, course.getLevel());
        assertEquals(expectedCoordinator, course.getCoordinator());
    }
}
