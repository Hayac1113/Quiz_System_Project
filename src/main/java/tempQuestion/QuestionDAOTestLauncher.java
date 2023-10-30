package tempQuestion;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import model.Question;
import model.Quiz;

public class QuestionDAOTestLauncher {
    public static void main(String[] args) {
        // stap 1: connectie maken
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster",
                "userQuizMasterPW");
        dBaccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);

        // Question opslaan in de database
        Quiz testQuiz = new Quiz("testquiz", "easy", 1, "testcursus");

        questionDAO.storeOne(new Question("Welk antwoord is juist?", "juist",
                "fout1", "fout2", "fout3", testQuiz));

    }
}
