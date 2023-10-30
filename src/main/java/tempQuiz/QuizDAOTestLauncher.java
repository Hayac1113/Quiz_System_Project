package tempQuiz;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import model.Quiz;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QuizDAOTestLauncher {
    public static void main(String[] args) {
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dBaccess);

        // Foreign key constraint uit doen
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("Set FOREIGN_KEY_CHECKS = 0");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        quizDAO.storeOne(new Quiz("TestNaam", "TestLevel", 123, "TestCursus"));
        quizDAO.storeOne(new Quiz("TestNaam2", "TestLevel", 123, "TestCursus"));
        quizDAO.storeOne(new Quiz("TestNaam3", "TestLevel", 123, "TestCursus"));
        quizDAO.storeOne(new Quiz("TestNaam4", "TestLevel", 123, "TestCursus"));

        // Foreign key constraint aandoen
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("Set FOREIGN_KEY_CHECKS = 1");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Quiz> quizInTheDatabase = quizDAO.getAll();
        for (Quiz quiz : quizInTheDatabase) {
            System.out.println(quiz);
        }

        System.out.println(quizDAO.getOneByPK("TestNaam2"));
    }
}
