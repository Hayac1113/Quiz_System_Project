package tempQuiz;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javacouchdb.AbstractCouchDBDAO;
import javacouchdb.CouchDBaccess;
import model.Question;
import model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizCouchDBDAO extends AbstractCouchDBDAO {
    private static Gson gson;

    public QuizCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }

    public String saveSingleQuiz(Quiz quiz) {
        String jsonString = gson.toJson(quiz);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        return saveDocument(jsonObject);
    }

    public Quiz getQuiz(String quizName){
        Quiz resultaat;
        for (JsonObject jsonObject :getAllDocuments()){
            resultaat = gson.fromJson(jsonObject, Quiz.class);
            if (resultaat.getQuizName().equals(quizName)){
                return resultaat;
            }
        }
        return null;
    }

    public List<Quiz> getAllQuiz(){
        List<Quiz> listResults = new ArrayList<>();
        Quiz result;
        for (JsonObject jsonObject : getAllDocuments()){
            result = gson.fromJson(jsonObject, Quiz.class);
            listResults.add(result);
        }
        return listResults;
    }

}
