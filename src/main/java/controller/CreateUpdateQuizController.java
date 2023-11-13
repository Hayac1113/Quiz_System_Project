package controller;


import database.mysql.CourseDAO;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.Course;
import model.Quiz;
import view.Main;

import java.util.ArrayList;
import java.util.List;


public class CreateUpdateQuizController {
    private final QuizDAO quizDAO;
    private final CourseDAO courseDAO;
    private final QuestionDAO questionDAO;
    private static Quiz classQuiz;

    @FXML
    private Label viewTitle;
    @FXML
    private TextField insertQuizName;
    @FXML
    private ComboBox<String> insertQuizLevel;
    @FXML
    private ComboBox<Integer> insertQuizSuccess;
    @FXML
    private ComboBox<Course> dropmenuCourse;

    public CreateUpdateQuizController() {
        this.questionDAO = new QuestionDAO(Main.getDBaccess());
        this.quizDAO = new QuizDAO(Main.getDBaccess());
        this.courseDAO = new CourseDAO(Main.getDBaccess());
    }

    public void setup(Quiz quiz) {
        classQuiz = quiz;

        setDropmenuLevel();
        setDropmenuSuccess(quiz.getQuizId());
        setDropmenuCourse();

        if (quiz.getQuizId() != 0) { //op Wijzig knop gedruk in ManageQuizzes
            viewTitle.setText("Wijzig Quiz");
            insertQuizName.setText(quiz.getQuizName());
            insertQuizLevel.getSelectionModel().select(quiz.getLevel());
            insertQuizSuccess.getSelectionModel().select(quiz.getSuccesDefinition());
            dropmenuCourse.getSelectionModel().select(quiz.getCourse());
        }

    }

    public void doMenu() {
        Main.getSceneManager().showManageQuizScene(classQuiz.getCourse());
    }

    public void doCreateUpdateQuiz() {
        // check voor lege velden
        if (insertQuizName.getText().isEmpty() || insertQuizLevel.getSelectionModel().isEmpty() ||
                insertQuizSuccess.getSelectionModel().isEmpty() || dropmenuCourse.getValue() == null) {
            Alert emptyfield = new Alert(Alert.AlertType.ERROR);
            emptyfield.setTitle("QuizMaster");
            emptyfield.setHeaderText("Opslaan is niet mogelijk");
            emptyfield.setContentText(checkEmptyfield());
            emptyfield.show();
            // quiz wijzigen in database
        } else if (classQuiz.getQuizId() != 0) {
            Quiz modifyQuiz = new Quiz(classQuiz.getQuizId(), insertQuizName.getText(), insertQuizLevel.getValue(),
                    insertQuizSuccess.getValue(), dropmenuCourse.getValue());
            quizDAO.updateOne(modifyQuiz);
            Alert modified = new Alert(Alert.AlertType.INFORMATION);
            modified.setTitle("QuizMaster");
            modified.setHeaderText("Quiz: " + modifyQuiz.getQuizName());
            modified.setContentText("Aangepast");
            showAndCloseAlert(modified);
            // check voor unieke quizNaam
        } else if (quizDAO.findByName(insertQuizName.getText().trim())) {
            Alert uniqueName = new Alert(Alert.AlertType.ERROR);
            uniqueName.setTitle("QuizMaster");
            uniqueName.setHeaderText("Opslaan is niet mogelijk");
            uniqueName.setContentText("Naam moet uniek zijn.");
            uniqueName.show();
            // nieuwe quiz wegschrijven naar database
        } else if (classQuiz.getQuizId() == 0) { //heeft geen quizId dus op Nieuwe Quiz gedrukt
            Quiz newQuiz = new Quiz(insertQuizName.getText(), insertQuizLevel.getValue(),
                    insertQuizSuccess.getValue(), dropmenuCourse.getValue());
            quizDAO.storeOne(newQuiz);
            Alert saved = new Alert(Alert.AlertType.INFORMATION);
            saved.setTitle("Quizmaster");
            saved.setHeaderText("Quiz: " + newQuiz.getQuizName());
            saved.setContentText("Toegevoegd aan: " + newQuiz.getCourse().getCourseName());
            showAndCloseAlert(saved);

        }
    }

    public String checkEmptyfield() {
        StringBuilder waarschuwing = new StringBuilder();

        if (insertQuizName.getText().isEmpty()) {
            waarschuwing.append("Quiznaam ontbreekt. Vul een gebruikersnaam in.\n");
        }
        if (insertQuizLevel.getSelectionModel().isEmpty()) {
            waarschuwing.append("Quiz nivea is niet geselecteerd. Selecteer een niveau.\n");
        }
        if (insertQuizSuccess.getSelectionModel().isEmpty()) {
            waarschuwing.append("Quiz success definitie is niet geselecteerd. Selecteer een aantal.\n");
        }
        if (dropmenuCourse.getValue() == null) {
            waarschuwing.append("Cursus is niet geselecteerd. Selecteer een cursus. \n");
        }

        return waarschuwing.toString();
    }


    public void setDropmenuLevel() {
        insertQuizLevel.getItems().add("Beginner");
        insertQuizLevel.getItems().add("Medium");
        insertQuizLevel.getItems().add("Gevorderd");
    }

    public void setDropmenuSuccess(int quizId) {
        int numberOfQuestions = questionDAO.getNumberOfQuestions(quizId);
        List<Integer> rangeNumbers = new ArrayList<>();
        for (int i = 0; i <= numberOfQuestions; i++) {
            rangeNumbers.add(i);
        }
        insertQuizSuccess.getItems().addAll(rangeNumbers);
    }

    public void setDropmenuCourse() {
        List<Course> courses = courseDAO.getCoursesByUserId(classQuiz.getCourse().getCoordinator().getUserId());
        dropmenuCourse.getItems().addAll(courses);

        dropmenuCourse.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course.getCourseName();
            }

            @Override
            public Course fromString(String s) {
                return null;
            }
        });
    }

    public void showAndCloseAlert(Alert alert) {
        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            alert.close();
            Main.getSceneManager().showManageQuizScene(classQuiz.getCourse());
        });
        delay.play();

    }
}


