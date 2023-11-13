package controller;

import database.mysql.CourseDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Course;
import view.Main;
import javafx.stage.FileChooser;
import java.io.File;
import javacouchdb.CouchDBaccess;
import javacouchdb.QuizResultCouchDBDAO;
import java.util.ArrayList;
import java.util.List;

public class ManageCoursesController {
    private final CourseDAO courseDAO;
    private final QuizResultCouchDBDAO quizResultDAO;
    @FXML
    ListView<Course> courseList;
    @FXML
    private Label studentCounter;

    public ManageCoursesController() {
        String databaseName = "quizmaster";   // Pas actual database name toe
        String mainUser = "admin";           // Past actual main username toe
        String mainUserPassword = "admin";    // Pas actual main user password toe

        // Maak CouchDBaccess object met de vereiste credentials
        CouchDBaccess couchDBaccess = new CouchDBaccess(databaseName, mainUser, mainUserPassword);
        this.quizResultDAO = new QuizResultCouchDBDAO(couchDBaccess);
        this.courseDAO = new CourseDAO(Main.getDBaccess());
    }

    public void setup() {
        List<Course> courses = courseDAO.getAll();
        courseList.getItems().addAll(courses);
        courseList.setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");

        // Getoonde tekst in cellen in ListView courseList aanpassen
        courseList.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null || course.getCourseName() == null) {
                    setText(null);
                } else {
                    setText(course.getCourseName());
                }
            }
        });
        courseList.getSelectionModel().selectFirst();
        // Voeg listener toe voor selection.
        courseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateStudentCounter(newValue));
        // Update the counter for the initially selected course.
        updateStudentCounter(courseList.getSelectionModel().getSelectedItem());
    }

    private void updateStudentCounter(Course course) {
        if (course != null) {
            int studentCount = courseDAO.countStudentsForCourse(course.getCourseId());
            studentCounter.setText(studentCount + " student(en) ingeschreven voor deze cursus.");
        } else {
            studentCounter.setText(" ");
        }
    }

    @FXML
    public void doMenu(ActionEvent actionEvent){
        Main.getSceneManager().showLoginScene();
    }

    public void doCreateCourse(ActionEvent actionEvent){
        Course course = new Course();
        Main.getSceneManager().showCreateUpdateCourseScene(course);
    }

    public void doUpdateCourse(){
        Course course = courseList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateCourseScene(course);
    }

    // Methode om een cursus te verwijderen en de gebruiker een melding te geven
    public void doDeleteCourse(){
        Course course = courseList.getSelectionModel().getSelectedItem();
        courseDAO.deleteOne(course);
        Alert delete = new Alert(Alert.AlertType.INFORMATION);
        delete.setTitle("QuizMaster");
        delete.setHeaderText("Cursus " + course.getCourseName() + " is succesvol verwijderd");
        delete.setContentText(" ");
        delete.showAndWait();
        Main.getSceneManager().showManageCoursesScene();
    }

    // Methode om een rapport te genereren van alle cursussen en het aantal studenten dat is ingeschreven
    @FXML
    public void generateReport(ActionEvent event) {
        CourseDAO courseDAO = new CourseDAO(Main.getDBaccess());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Opslaan Rapport");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tekstbestanden", "*.txt"),
                new FileChooser.ExtensionFilter("Alle Bestanden", "*.*"));

        // Default filenaam voor opslaan
        fileChooser.setInitialFileName("cursus_overzicht.txt");

        // Toon de verkenner
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            courseDAO.createCourseReport(file.getAbsolutePath());
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

    @FXML
    protected void handleSaveOverview() {
        String filePath = "src/main/resources/PrintsQuizmaster/studentfeedback.txt"; // default path voor opslaan
        try {
            quizResultDAO.saveStudentsWithMinOneQuizToFile(filePath);
            showAlert("Success", "Het overzicht is opgeslagen in " + filePath, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Opslaan mislukt: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
