package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Cursus;
import model.Question;
import model.Quiz;

public class CoordinatorDashboardController {

    @FXML
    private ListView<Cursus> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<Question> questionList;

    public void setup() {
        courseList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldCourse, newCourse) ->
                        System.out.println("Geselecteerde cursus: " + observableValue + ", " + oldCourse + ", " + newCourse));

        quizList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldQuiz, newQuiz) ->
                        System.out.println("Geselecteerde quiz: " + observableValue + ", " + oldQuiz + ", " + newQuiz));
    }

    public void doNewQuiz() { }

    public void doEditQuiz() { }

    public void doNewQuestion() { }

    public void doEditQuestion() { }

    public void doMenu() { }
}
