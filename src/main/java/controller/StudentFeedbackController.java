package controller;

import database.mysql.CourseDAO;
import database.mysql.QuizDAO;
import javacouchdb.CouchDBaccess;
import javacouchdb.QuizResultCouchDBDAO;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Quiz;
import model.QuizResult;
import view.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class StudentFeedbackController {
    private final QuizResultCouchDBDAO quizResultCouchDBDAO;
    private int userId;
    private int quizId;
    @FXML
    private Label feedbackLabel;
    @FXML
    private ListView<QuizResult> feedbackList;

    public StudentFeedbackController() {
        this.quizResultCouchDBDAO = new QuizResultCouchDBDAO(Main.getCouchDBaccess());
    }

    public void setup(int userId, Quiz quiz) {
        this.feedbackLabel.setText(String.format("Feedback voor quiz %s", quiz.getQuizName()));
        this.userId = userId;
        quizId = quiz.getQuizId();
        List<QuizResult> quizResults = quizResultCouchDBDAO.getAllForUserQuiz(userId, quizId);
        Collections.sort(quizResults, Collections.reverseOrder());
        feedbackList.getItems().addAll(quizResults);
        //Getoonde tekst ListView quizList aanpassen
        feedbackList.setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");
        feedbackList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(QuizResult quizResult, boolean empty) {
                super.updateItem(quizResult, empty);
                if (empty) {
                    setText(null);
                } else {
                    double score = (double) quizResult.getNumberOfCorrectAnswers()*10/quizResult.getTotalNumberOfQuestions();
                    boolean isPass;
                    if (quizResult.getNumberOfCorrectAnswers() >= quizResult.getTotalNumberOfQuestions()) {
                        isPass = true;
                    } else {
                        isPass = false;
                    }
                    setText(String.format("Poging op %s,   %s,   %s", LocalDateTime.parse(quizResult.getDatetime()).format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")), String.format("Score: %.1f", score), String.format("Gehaald: %s", isPass ? "JA" : "NEE")));
                }
            }
        });
    }

    public void doMenu() {
        Main.getSceneManager().showSelectQuizForStudent(userId);
    }
}

