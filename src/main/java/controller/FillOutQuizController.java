package controller;

import database.mysql.CourseDAO;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javacouchdb.CouchDBaccess;
import javacouchdb.QuizResultCouchDBDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Question;
import model.Quiz;
import model.QuizResult;
import view.Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FillOutQuizController {

    private final QuestionDAO questionDAO = new QuestionDAO(Main.getDBaccess());
    private final QuizDAO quizDAO = new QuizDAO(Main.getDBaccess());
    CouchDBaccess couchDBaccess = new CouchDBaccess("quizmaster","admin", "admin");
    private final QuizResultCouchDBDAO quizResultCouchDBDAO = new QuizResultCouchDBDAO(couchDBaccess);
    List<Question> questionsQuiz = new ArrayList<>();
    private int currentQuestionNumber;
    private int vraagNummer = 1;
    private int correctAnswers;
    private int loginUserId;
    private int quizId;

    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;
    @FXML
    private Button answerA;
    @FXML
    private Button answerB;
    @FXML
    private Button answerC;
    @FXML
    private Button answerD;
    @FXML
    private Button previousQuestion;
    @FXML
    private Button nextQuestion;
    @FXML
    private Button finishQuiz;
    @FXML
    private Button back;

    public void setup(int quizId, int userId) {
        loginUserId = userId;
        this.quizId = quizId;
        //verzamel de vragen die horen bij ingegeven quizId
        questionsQuiz = questionDAO.getQuestionsByQuizId(quizId);
        //schud de vragen door elkaar
        Collections.shuffle(questionsQuiz);
        //beginnen met de 1e vraag
        currentQuestionNumber = 0;
        //score initializeren
        correctAnswers = 0;
        //Titel
        titleLabel.setText("Vraag " + vraagNummer);
        //1e vraag tonen
        displayQuestion();
    }
    private void displayQuestion() {
        //zolang currenctQuestionNumber tussen 0 en size van questionQuiz zit,
        // wordt er een nieuwe vraag met antwoorden getoond
        if (currentQuestionNumber >= 0 && currentQuestionNumber < questionsQuiz.size()){
            Question currentQuestion = questionsQuiz.get(currentQuestionNumber);

            //lijst van antwoorden maken en deze door elkaar shuffelen
            ArrayList<String> answerChoices = new ArrayList<>();
            answerChoices.add(currentQuestion.getCorrectAnswer());
            answerChoices.add(currentQuestion.getAnswer2());
            answerChoices.add(currentQuestion.getAnswer3());
            answerChoices.add(currentQuestion.getAnswer4());
            Collections.shuffle(answerChoices);
            //Toon de vraag en de geshuffelde antwoorden in de textarea
            String question = currentQuestion.getTextQuestion() + "\n";
            String answers = "A. " + answerChoices.get(0) + "\n" +
                    "B. " + answerChoices.get(1) + "\n" +
                    "C. " + answerChoices.get(2) + "\n" +
                    "D. " + answerChoices.get(3);
            questionArea.setText(question + answers);

            //Button acties instellen
            answerA.setOnAction(event -> checkAnswer(currentQuestion, answerChoices.get(0)));
            answerB.setOnAction(event -> checkAnswer(currentQuestion, answerChoices.get(1)));
            answerC.setOnAction(event -> checkAnswer(currentQuestion, answerChoices.get(2)));
            answerD.setOnAction(event -> checkAnswer(currentQuestion, answerChoices.get(3)));

            //aangeven wanneer knoppen next en previous niet werken
            nextQuestion.setVisible(currentQuestionNumber != questionsQuiz.size() - 1);
            finishQuiz.setVisible(currentQuestionNumber == questionsQuiz.size() - 1);
            previousQuestion.setDisable(currentQuestionNumber == 0);
        }
    }
    //Methode die controleert of de String van het gekozen antwoord van de currentQuestion
    //overeenkomt met het attribuut correctAnswer van de currentQuestion.
    //Als dit het geval is gaat correctAnswers 1 omhoog
    private void checkAnswer(Question currentQuestion, String selectedAnswer) {
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;

        }
        //volgende vraag tonen
        currentQuestionNumber++;
        vraagNummer++;
        titleLabel.setText("Vraag " + vraagNummer);
        displayQuestion();
    }

    //volgende vraag tonen
    public void doNextQuestion() {
        currentQuestionNumber++;
        vraagNummer++;
        titleLabel.setText("Vraag " + vraagNummer);
        displayQuestion();
    }
    //vorige vraag tonen
    public void doPreviousQuestion() {
        currentQuestionNumber--;
        vraagNummer--;
        titleLabel.setText("Vraag " + vraagNummer);
        displayQuestion();
    }

    public void doMenu() {
        Main.getSceneManager().showSelectQuizForStudent(loginUserId);
    }

    public void doFinishQuiz () {
        QuizResult quizResult = new QuizResult(loginUserId, quizId, quizDAO.getOneById(quizId).getSuccesDefinition(), LocalDateTime.now().toString(), correctAnswers, questionsQuiz.size());
        quizResultCouchDBDAO.saveSingleQuizResult(quizResult);
        Main.getSceneManager().showStudentFeedback(loginUserId, quizDAO.getOneById(quizId));
    }
}
