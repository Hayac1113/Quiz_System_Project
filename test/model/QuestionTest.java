package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void testToString() {
        //arrange
        Question q = new Question();

        String actual = q.toString();
        String expected = "onbekend " + "onbekend " + "onbekend " + "onbekend " + "onbekend " + "Onbekend\n";

        assertEquals(expected, actual);
    }

    @Test
    void getCorrectAnswer() {
        Question g = new Question();
        String actual = g.getCorrectAnswer();
        String expected = "onbekend";
    }
}