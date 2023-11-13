package tempQuiz;

import com.google.gson.JsonObject;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javacouchdb.CouchDBaccess;
import model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizNoSQLLauncher {

    private static CouchDBaccess couchDBaccess;
    private static QuizCouchDBDAO quizCouchDAO;


    public static void main(String[] args) {
        //Access to CouchDB
        couchDBaccess = new CouchDBaccess("quizmaster", "admin", "admin");
        quizCouchDAO = new QuizCouchDBDAO(couchDBaccess);

        //Access to MySQLserver
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dBaccess);

        List<Quiz> listQuizzes = quizDAO.getAll();

        //wegschrijven naar couchDB
        if (couchDBaccess.getClient() != null) {
            System.out.println("Connection open");
        }

        for (Quiz quiz: listQuizzes) {
            quizCouchDAO.saveSingleQuiz(quiz);
        }

        //ophalen van couchDB
        List<Quiz> quizList = quizCouchDAO.getAllQuiz();
        Quiz quiz = quizCouchDAO.getQuiz("Engels A 1");

        System.out.println(quizList);
        System.out.println(quiz);
    }
}
