package controller;

import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class ManageQuestionsController {

    private final QuestionDAO questionDAO;
    private static Quiz classQuiz;
    @FXML
    ListView<Question> questionList;

    public ManageQuestionsController() {
        this.questionDAO = new QuestionDAO(Main.getDBaccess());
    }

    public void setup(Quiz quiz) {
        classQuiz = quiz;
        List<Question> questions = questionDAO.getQuestionsByQuizId(classQuiz.getQuizId());
        questionList.getItems().addAll(questions);
        questionList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Question question, boolean empty) { //check voor null kan er misschien uit
                super.updateItem(question, empty);
                setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");
                if (empty || question == null || question.getTextQuestion() == null) {
                    setText(null);
                } else {
                    setText(question.getTextQuestion());
                }
            }
        });

        questionList.getSelectionModel().selectFirst();

    }

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard(classQuiz.getCourse().getCoordinator().getUserId());
    }

    public void doCreateQuestion() {
        Question question = new Question(0,"onbekend","onbekend","onbekend",
                "onbekend","onbekend", classQuiz);
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doUpdateQuestion() {
        Question question = questionList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doDeleteQuestion() {
        Question question = questionList.getSelectionModel().getSelectedItem();
        questionDAO.deleteOne(question);
        Alert delete = new Alert(Alert.AlertType.INFORMATION);
        delete.setTitle("QuizMaster");
        delete.setHeaderText("Vraag: " + question.getTextQuestion());
        delete.setContentText("Verwijderd uit: " + question.getQuiz().getQuizName());
        delete.showAndWait();
        Main.getSceneManager().showManageQuestionsScene(classQuiz);
    }
}
