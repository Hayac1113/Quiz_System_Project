package tempQuiz;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import model.Course;
import model.Quiz;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QuizDAOTestLauncher {
    public static void main(String[] args) {
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dBaccess);

        quizDAO.storeOne(new Quiz("TestNaam", "TestLevel", 123, new Course()));
        quizDAO.storeOne(new Quiz("TestNaam2", "TestLevel", 123, new Course()));
        quizDAO.storeOne(new Quiz("TestNaam3", "TestLevel", 123, new Course()));
        quizDAO.storeOne(new Quiz("TestNaam4", "TestLevel", 123, new Course()));

        System.out.println(quizDAO.getOneById(5));
    }
}
