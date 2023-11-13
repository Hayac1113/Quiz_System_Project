package controller;

import database.mysql.CourseDAO;
import database.mysql.QuizDAO;
import javacouchdb.CouchDBaccess;
import javacouchdb.QuizResultCouchDBDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Course;
import model.Quiz;
import model.QuizResult;
import view.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SelectQuizForStudentController {

    private final CourseDAO courseDAO;
    private final QuizDAO quizDAO;
    private final QuizResultCouchDBDAO quizResultCouchDBDAO;
    private int loginUserId;
    @FXML
    ListView<Quiz> quizList;

    public SelectQuizForStudentController() {
        this.courseDAO = new CourseDAO(Main.getDBaccess());
        this.quizDAO = new QuizDAO(Main.getDBaccess());
         this.quizResultCouchDBDAO = new QuizResultCouchDBDAO(Main.getCouchDBaccess());
    }

    public void setup(int userId) {
        loginUserId = userId;
        List<Course> courses = courseDAO.getCoursesByStudent(userId);
        for (Course course : courses) {
            quizList.getItems().addAll(quizDAO.getQuizzesByCourseId(course.getCourseId()));
        }


        //Getoonde tekst ListView quizList aanpassen
        quizList.setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");
        quizList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Quiz quiz, boolean empty) {
                super.updateItem(quiz, empty);
                if (empty) {
                    setText(null);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.format("%-30s    ", quiz.getQuizName())); // eventueel toevoegen: "  (Cursus: " + quiz.getCourse().getCourseName() + ")"
                    QuizResult lastQuizResult = quizResultCouchDBDAO.getLast(userId, quiz.getQuizId());
                    if (lastQuizResult != null) {
                        double score = (double) lastQuizResult.getNumberOfCorrectAnswers()*10 / lastQuizResult.getTotalNumberOfQuestions();
                        boolean isPass;
                        if (lastQuizResult.getNumberOfCorrectAnswers() >= lastQuizResult.getTotalNumberOfQuestions()) {
                            isPass = true;
                        } else {
                            isPass = false;
                        }
                        stringBuilder.append(String.format("Laatste poging op %s,   %s,   %s", LocalDateTime.parse(lastQuizResult.getDatetime()).format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")), String.format("Score: %.1f", score), String.format("Gehaald: %s", isPass ? "JA" : "NEE")));
                    }
                    setText(stringBuilder.toString());
                }
            }
        });
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(loginUserId);
    }

    public void doQuiz() {
        Main.getSceneManager().showFillOutQuiz(quizList.getSelectionModel().getSelectedItem().getQuizId(), loginUserId);
    }
}
