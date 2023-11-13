package tempQuestion;

import com.google.gson.Gson;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javacouchdb.CouchDBaccess;
import javacouchdb.QuestionCouchDBAO;
import model.Question;
import model.Quiz;
import view.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CouchDBQuestionLauncher {
    private static CouchDBaccess couchDBaccess;
    private static QuestionCouchDBAO questionCouchDBAO;



    public static void main(String[] args) {
        // Instellingen om connectie te kunnen maken met NoSQL
        couchDBaccess = new CouchDBaccess("questions","Couch","dontsit10");
        questionCouchDBAO = new QuestionCouchDBAO(couchDBaccess);
        // Connectie met QuestionDAO om de Questions uit MySQL op te halen
        QuestionDAO questionDAO = new QuestionDAO(Main.getDBaccess());
        List<Question> questionList = questionDAO.getAll();
        //Arraylist van Questions omvormen tot JSON bestanden en opslaan op NoSQL
        saveQuestionList(questionList);
    }

    private static void saveQuestionList(List<Question> questionList) {
//		Maak een connectie met CouchDB, gebruik het CouchDBaccess object.
// 		getClient() roept couchDBaccess.openConnection() aan; mocht de connection niet lukken dan is client == null;
        if	(couchDBaccess.getClient() != null) {
            System.out.println("Connection open");
//		Sla alle Questions op in de CouchDb database.
            for (Question question : questionList) {
                questionCouchDBAO.saveSingleQuestion(question);
            }
        }
    }

}
