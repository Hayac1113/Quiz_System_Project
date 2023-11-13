package controller;

import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.List;

public class CreateUpdateQuestionController {
    private final QuestionDAO questionDAO;
    private final QuizDAO quizDAO;
    private static Question classQuestion;


    @FXML
    private Label labelTask;

    @FXML
    private TextArea questionIdTextArea;

    @FXML
    private TextArea textQuestionTextArea;

    @FXML
    private TextArea correctAnswerTextArea;

    @FXML
    private TextArea answer2TextArea;

    @FXML
    private TextArea answer3TextArea;

    @FXML
    private TextArea answer4TextArea;


    @FXML
    private ComboBox<Quiz> dropmenuQuiz;

    public CreateUpdateQuestionController() {
        this.questionDAO = new QuestionDAO(Main.getDBaccess());
        this.quizDAO = new QuizDAO(Main.getDBaccess());
    }

    public void setup(Question question) {
        classQuestion = question;
        labelTask.setText("Vraag aanmaken");

        //dropmenu toevoegen voor kolom Quiz
        List<Quiz> quizzes = quizDAO.getAll();
        dropmenuQuiz.getItems().addAll(quizzes);


        //alleen naam van quiz printen
        dropmenuQuiz.setConverter(new StringConverter<Quiz>() {
            @Override
            public String toString(Quiz quiz) {
                return quiz.getQuizName();
            }


            @Override
            public Quiz fromString(String s) {
                return null;
            }
        });
        if (question.getQuestionId() != 0) { //op Wijzig knop gedruk in ManageQuestions
            labelTask.setText("Wijzig Vraag");
            questionIdTextArea.setText(String.valueOf(question.getQuestionId()));
            textQuestionTextArea.setText(question.getTextQuestion());
            correctAnswerTextArea.setText(question.getCorrectAnswer());
            answer2TextArea.setText(question.getAnswer2());
            answer3TextArea.setText(question.getAnswer3());
            answer4TextArea.setText(question.getAnswer4());
            dropmenuQuiz.getSelectionModel().select(question.getQuiz());


        }
    }


    public void doMenu(ActionEvent actionEvent) {
        // Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doQuestionList(ActionEvent actionEvent) {
        Main.getSceneManager().showManageQuestionsScene(classQuestion.getQuiz());
    }

    @FXML
    public void doCreateUpdateQuestion() {
        StringBuilder waarschuwing = new StringBuilder();
        boolean correcteInvoer = true;
        String textQuestion = textQuestionTextArea.getText();
        String correctAnswer = correctAnswerTextArea.getText();
        String answer2 = answer2TextArea.getText();
        String answer3 = answer3TextArea.getText();
        String answer4 = answer4TextArea.getText();
        Quiz quiz = dropmenuQuiz.getValue();

        if (textQuestion.isEmpty()) {
            waarschuwing.append("Vraag ontbreekt. Vul een vraag in.\n");
            correcteInvoer = false;
        }
        if (correctAnswer.isEmpty()) {
            waarschuwing.append("Juist antwoord ontbreekt. Vul een juist antwoord in.\n");
            correcteInvoer = false;
        }
        if (answer2.isEmpty()) {
            waarschuwing.append("Antwoord 2 ontbreekt. Vul antwoord 2 in.\n");
            correcteInvoer = false;
        }
        if (answer3.isEmpty()) {
            waarschuwing.append("Antwoord 3 ontbreekt. Vul antwoord 3 in.\n");
            correcteInvoer = false;
        }
        if (answer4.isEmpty()) {
            waarschuwing.append("Antwoord 4 ontbreekt. Vul antwoord 4 in.\n");
            correcteInvoer = false;
        }
        if (quiz == null) {
            waarschuwing.append("Quiz ontbreekt. Selecteer een quiz.\n");
            correcteInvoer = false;
        }


        if (!correcteInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setTitle("QuizMaster");
            foutmelding.setHeaderText("Opslaan niet mogelijk");
            foutmelding.setContentText(waarschuwing.toString());
            foutmelding.show();

        } else {
            if (questionIdTextArea.getText() == null || questionIdTextArea.getText().trim().isEmpty()) {
                Question newQuestion = new Question(textQuestionTextArea.getText(), correctAnswerTextArea.getText(),
                        answer2TextArea.getText(), answer3TextArea.getText(), answer4TextArea.getText(), dropmenuQuiz.getValue());
                questionDAO.storeOne(newQuestion);
                Alert saved = new Alert(Alert.AlertType.INFORMATION);
                saved.setTitle("QuizMaster");
                saved.setHeaderText("Vraag: " + newQuestion.getTextQuestion());
                saved.setContentText("Toegevoegd aan: " + newQuestion.getQuiz().getQuizName());
                saved.showAndWait();
                Main.getSceneManager().showCreateUpdateQuestionScene(classQuestion);

            } else {
                Question updateQuestion = new Question(Integer.parseInt(questionIdTextArea.getText()),
                        textQuestionTextArea.getText(), correctAnswerTextArea.getText(),
                        answer2TextArea.getText(), answer3TextArea.getText(),
                        answer4TextArea.getText(), dropmenuQuiz.getValue());
                questionDAO.updateOne(updateQuestion);
                Alert saved = new Alert(Alert.AlertType.INFORMATION);
                saved.setTitle("QuizMaster");
                saved.setHeaderText("Vraag: " + updateQuestion.getTextQuestion());
                saved.setContentText("Aangepast");
                saved.showAndWait();
                Main.getSceneManager().showManageQuestionsScene(classQuestion.getQuiz());
            }
        }
    }
}
