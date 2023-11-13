package javacouchdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Question;

public class QuestionCouchDBAO extends AbstractCouchDBDAO {

    private Gson gson;
    public QuestionCouchDBAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }

    public String saveSingleQuestion(Question question){
        String jsonString = gson.toJson(question);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        return saveDocument(jsonObject);
    }
}
