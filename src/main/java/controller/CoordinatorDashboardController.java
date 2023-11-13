package controller;

import database.mysql.CourseDAO;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import model.Course;
import model.Question;
import model.Quiz;
import view.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CoordinatorDashboardController {

    private final CourseDAO courseDAO = new CourseDAO(Main.getDBaccess());
    private final QuizDAO quizDAO = new QuizDAO(Main.getDBaccess());
    private final QuestionDAO questionDAO = new QuestionDAO(Main.getDBaccess());

    @FXML
    private ListView<Course> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<Question> questionList;
    @FXML
    private Label totalQuestions;


    public void setup(int userId) {
        List<Course> courses = courseDAO.getCoursesByUserId(userId);
        courseList.getItems().addAll(courses);
        courseList.setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");


        List<Quiz> quizzes = quizDAO.getAll();
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


        List<Question> questions = questionDAO.getAll();
        questionList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty);
                setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");
                if (empty || question == null || question.getTextQuestion() == null) {
                    setText(null);
                } else {
                    setText(question.getTextQuestion());
                }
            } // Override voor tonen van Quizname alleen (anders hele object)
        });


        //De listener aanmaken voor de courseList
        courseList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldCourse, newCourse) -> {
                    if (newCourse != null) {
                        //quizzes filteren op basis van de geselecteerde course
                        List<Quiz> filteredQuizzes = quizzes.stream().filter(quiz -> quiz.getCourse()
                                .getCourseName().equals(newCourse.getCourseName())).collect(Collectors.toList());
                        //quizlist updaten met de gefilterde quizzes
                        quizList.setItems(FXCollections.observableList(filteredQuizzes));
                    }
                    totalQuestions.setText("");
                });

        quizList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldQuiz, newQuiz) -> {
                    if (newQuiz != null) {
                        List<Question> filteredQuestions = questions.stream().filter(question -> question.getQuiz()
                                .getQuizName().equals(newQuiz.getQuizName())).collect(Collectors.toList());

                        questionList.setItems(FXCollections.observableList(filteredQuestions));
                    }
                    showAmountQuestions();
                });
    }

    public void showAmountQuestions() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        if (quiz != null) {
            int count = questionDAO.getNumberOfQuestions(quiz.getQuizId());
            totalQuestions.setText(String.format("Er zijn %d vragen in %s.", count, quiz.getQuizName()));
        }
    }

    public void generateQuizReport(String filename) {
        int totalQuestionCount = 0;
        int aantalQuiz = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : courseList.getItems()) {
                writer.write("Cursus: " + course.getCourseName());
                writer.newLine();
                List<Quiz> quizzesByCourse = quizDAO.getQuizzesByCourseId(course.getCourseId());
                for (Quiz quiz : quizzesByCourse) {
                    aantalQuiz += 1;
                    int questionCount = questionDAO.getNumberOfQuestions(quiz.getQuizId());
                    writer.write("  Quiz: " + quiz.getQuizName() + ", aantal vragen: "
                            + questionCount);
                    writer.newLine();
                    totalQuestionCount += questionCount;
                }
            }
            writer.newLine();
            writer.write("Het gemiddelde aantal vragen over alle cursussen is: " + totalQuestionCount / aantalQuiz);
        } catch (IOException e) {
            System.out.println("Er is een fout opgetreden tijdens het schrijven van het bestand: " + e.getMessage());
        }
    }

    public void generateQuestionReport(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : courseList.getItems()) {
                writer.write("Cursus: " + course.getCourseName());
                writer.newLine();
                List<Quiz> quizzesByCourse = quizDAO.getQuizzesByCourseId(course.getCourseId());
                for (Quiz quiz : quizzesByCourse) {
                    writer.write("  Quiz: " + quiz.getQuizName());
                    writer.newLine();
                    List<Question> questionByCourse = questionDAO.getQuestionsByQuizId(quiz.getQuizId());
                    for (int i = 0; i < questionByCourse.size(); i++) {
                        writer.write(String.format("        %d. %s", i + 1, questionByCourse.get(i).getTextQuestion()));
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Er is een fout opgetreden tijdens het schrijven van het bestand: " + e.getMessage());
        }
    }

    @FXML
    public void saveGeneratedReport(ActionEvent event) {
        //Welke Print knop is gedrukt
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Opslaan Rapport");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tekstbestanden", "*.txt"),
                new FileChooser.ExtensionFilter("Alle Bestanden", "*.*"));

        // Default filenaam voor opslaan
        if (value == 1) {
            fileChooser.setInitialFileName("quiz_overzicht.txt");
        } else {
            fileChooser.setInitialFileName("question_overzicht.txt");
        }

        // Toon de verkenner
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            if (value == 1) {
                generateQuizReport(file.getAbsolutePath());
            } else {
                generateQuestionReport(file.getAbsolutePath());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Het overzicht is gegenereerd");
            alert.setHeaderText(null);
            alert.setContentText("Het rapport is succesvol aangemaakt.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opslaan is mislukt");
            alert.setHeaderText(null);
            alert.setContentText("Geen bestand geselecteerd.");
            alert.showAndWait();
        }
    }

    public void doMenuQuestion() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        if (quiz != null) {
            Main.getSceneManager().showManageQuestionsScene(quiz);
        }
    }

    public void doMenuQuiz() {
        Course course = courseList.getSelectionModel().getSelectedItem();
        if (course != null) {
            Main.getSceneManager().showManageQuizScene(course);
        }
    }

    public void doMenu() {
        Main.getSceneManager().showLoginScene();
    }
}
