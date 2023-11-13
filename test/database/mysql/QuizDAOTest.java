package database.mysql;

import model.Course;
import model.Quiz;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizDAOTest {

    @Test
    void getOneById() {
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dBaccess);

        //Expected Quiz
        User user = new User(2, "knobbse", "Xt$G?'Rp%u", "Selvi", "", "Knobben", "Coördinator");
        Course course = new Course(3, "Java Programming", "Beginner", user);
        Quiz quiz = new Quiz(1, "Java Basis", "Beginner", 7, course);

        Quiz testQuiz = quizDAO.getOneById(1);

        assertEquals(quiz.getQuizId(), testQuiz.getQuizId());
    }
}