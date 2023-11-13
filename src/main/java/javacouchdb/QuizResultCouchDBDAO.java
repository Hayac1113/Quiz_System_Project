package javacouchdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import model.QuizResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class QuizResultCouchDBDAO extends AbstractCouchDBDAO {
    private Gson gson;

    public QuizResultCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }

    // QuizResult opslaan:
    public String saveSingleQuizResult(QuizResult quizResult) {
        String jsonString = gson.toJson(quizResult);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        return saveDocument(jsonObject);
    }

    // Alle QuizResults voor bepaalde gebruikersId en quizId uitlezen:
    public List<QuizResult> getAllForUserQuiz(int UserId, int QuizId) {
        List<QuizResult> quizResults = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            QuizResult result = gson.fromJson(jsonObject, QuizResult.class);
            if (result.getUserId() == UserId && result.getQuizId() == QuizId) {
                quizResults.add(result);
            }
        }
        return quizResults;
    }
    // Laatste QuizResult uitlezen:
    public QuizResult getLast(int userId, int quizId) {
        QuizResult lastQuizResult = null;
        for (JsonObject jsonObject : getAllDocuments()) {
            QuizResult result = gson.fromJson(jsonObject, QuizResult.class);
            if ((result.getUserId() == userId && result.getQuizId() == quizId) && lastQuizResult == null) {
                lastQuizResult = result;
            } else if ((result.getUserId() == userId && result.getQuizId() == quizId) && LocalDateTime.parse(result.getDatetime()).isAfter(LocalDateTime.parse(lastQuizResult.getDatetime()))) {
                lastQuizResult = result;
            }
        }
        return lastQuizResult;
    }

    // Gebruik getAllDocuments() uit AbstractCouchDBDAO om alle QuizResults op te halen.
    @FXML
    public void saveStudentsWithMinOneQuizToFile(String filePath) {
        Set<Integer> userIdsWithQuizzes = new HashSet<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            QuizResult result = gson.fromJson(jsonObject, QuizResult.class);
            userIdsWithQuizzes.add(result.getUserId()); // Voeg de user ID aan set
        }

        // Alle user die minimaal 1 keer een quiz heeft gedaan.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int userId : userIdsWithQuizzes) {
                writer.write("User ID: " + userId + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
