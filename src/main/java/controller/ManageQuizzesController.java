package controller;

import database.mysql.QuizDAO;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import model.Course;
import model.Quiz;
import view.Main;

import java.util.List;

public class ManageQuizzesController {

    private final QuizDAO quizDAO;
    private static Course classCourse;

    @FXML
    ListView<Quiz> quizList;

    public ManageQuizzesController() {
        this.quizDAO = new QuizDAO(Main.getDBaccess());
    }

    public void setup(Course course) {
        classCourse = course;
        List<Quiz> quizByCourse = quizDAO.getQuizzesByCourseId(course.getCourseId());
        quizList.getItems().addAll(quizByCourse);

        quizList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Quiz quiz, boolean empty) {
                super.updateItem(quiz, empty);
                setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");
                if (empty || quiz == null || quiz.getQuizName() == null) {
                    setText(null);
                } else {
                    setText(quiz.getQuizName());
                }
            } // Override voor tonen van Quizname alleen (anders hele object)
        });

        quizList.getSelectionModel().selectFirst();
    }

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard(classCourse.getCoordinator().getUserId());
    }

    public void doCreateQuiz() {
        Quiz quiz = new Quiz("Onbekend", "Onbekend", 0, classCourse);
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    public void doUpdateQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }


    public void doDeleteQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        quizDAO.deleteOne(quiz);
        Alert delete = new Alert(Alert.AlertType.INFORMATION);
        delete.setTitle("Quizmaster");
        delete.setHeaderText("Quiz " + quiz.getQuizName() + " verwijderd");
        delete.setContentText("");
        delete.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            delete.close();
            Main.getSceneManager().showManageQuizScene(classCourse);
        });
        delay.play();
    }

}
