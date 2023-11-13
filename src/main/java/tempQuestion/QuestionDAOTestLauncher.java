package tempQuestion;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import model.Course;
import model.Question;
import model.Quiz;

import java.sql.SQLException;
import java.sql.Statement;

public class QuestionDAOTestLauncher {
    public static void main(String[] args) {
//stap 1: connectie maken
        DBAccess dBaccess = new DBAccess("QuizMaster","userQuizMaster",
                "userQuizMasterPW");
        dBaccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);

        //Foreign key constraint uit doen
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Question opslaan in de database
        Quiz testQuiz = new Quiz(1,"testquiz", "easy", 1, new Course());

        questionDAO.storeOne(new Question("Welk antwoord is juist?", "juist",
                "fout1", "fout2","fout3", testQuiz));

        //Foreign key constraint aandoen
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println(questionDAO.getOneById(1));


    }
}
