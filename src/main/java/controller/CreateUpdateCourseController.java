package controller;

import database.mysql.CourseDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.Course;
import model.User;
import view.Main;

import java.util.List;
import java.util.stream.Collectors;

public class CreateUpdateCourseController {
    private final CourseDAO courseDAO;
    private final UserDAO userDAO;

    @FXML
    private Label titelLabel;
    @FXML
    private Label courseIdLabel;
    @FXML
    private TextField courseNameTextField;
    @FXML
    private ComboBox<User> coordinatorComboBox;
    @FXML
    private ComboBox<String> levelComboBox;

    public CreateUpdateCourseController() {
        this.courseDAO = new CourseDAO(Main.getDBaccess());
        this.userDAO = new UserDAO(Main.getDBaccess());
    }

    private void getLevelComboBox() {
        List<String> levels = List.of("Beginner", "Medium", "Gevorderd");
        levelComboBox.getItems().setAll(levels);
    }
    public void initialize() {
        getLevelComboBox();
    }

    public void setup(Course course) {
//        dropmenu toevoegen voor kolom Coordinator
        List<User> allUsers = userDAO.getAll();
        // Filter the users to include only those with the "coordinator" role.
        List<User> coordinators = allUsers.stream()
                .filter(user -> "coördinator".equalsIgnoreCase(user.getRole()))
                .collect(Collectors.toList());
        coordinatorComboBox.getItems().addAll(coordinators);

        //alleen naam van coordinator met role coördinator printen
        coordinatorComboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getFirstName()+" "+user.getLastName();
            }

            @Override
            public User fromString(String s) {
                return null; // Conversion from string back to User is not supported in this context
            }
        });

        if (course.getCourseId() != 0) { //op Wijzig knop gedruk in ManageCourses
            titelLabel.setText("Wijzig Cursus");
            courseIdLabel.setVisible(true);
            courseIdLabel.setText(String.valueOf(course.getCourseId()));
            courseNameTextField.setText(course.getCourseName());
            levelComboBox.getSelectionModel().select(course.getLevel());
        } else {
            titelLabel.setText("Maak Nieuwe Cursus");
            courseIdLabel.setVisible(false); // Hide the label if creating a new course
        }
    }

    @FXML
    public void doMenu() {
         Main.getSceneManager().showManageCoursesScene();
    }
    @FXML
    private Course doCreateUpdateCourse() {
        // Check if any of the fields are empty
        if (courseNameTextField.getText().isEmpty() ||
                levelComboBox.getValue() == null ||
                coordinatorComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("QuizMaster");
            alert.setHeaderText("Fout bij aanmaken cursus");
            alert.setContentText("Een of meedere velden ontbreken.");
            alert.showAndWait();
            return null;
        }

        Course existingCourse = courseDAO.findByName(courseNameTextField.getText());

        if (courseIdLabel.getText() == null || courseIdLabel.getText().isEmpty()) {
            // Check if course already exists
            if (existingCourse != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("QuizMaster");
                alert.setHeaderText("Fout bij aanmaken cursus");
                alert.setContentText("De cursus " + courseNameTextField.getText() + " is al aangemaakt.");
                alert.showAndWait();
                return null;
            }

            // Create a new course
            Course newCourse = new Course(
                    courseNameTextField.getText(),
                    levelComboBox.getValue(),
                    coordinatorComboBox.getValue());
            courseDAO.storeOne(newCourse);
            Alert saved = new Alert(Alert.AlertType.INFORMATION);
            saved.setTitle("QuizMaster");
            saved.setHeaderText("Cursus " + newCourse.getCourseName() + " is succesvol aangemaakt");
            saved.showAndWait();
            Main.getSceneManager().showManageCoursesScene();
        } else {
            // Update an existing course
            Course updatedCourse = new Course(
                    Integer.parseInt(courseIdLabel.getText()),
                    courseNameTextField.getText(),
                    levelComboBox.getValue(),
                    coordinatorComboBox.getValue());
            courseDAO.updateOne(updatedCourse);
            Alert saved = new Alert(Alert.AlertType.INFORMATION);
            saved.setTitle("QuizMaster");
            saved.setHeaderText("Cursus " + updatedCourse.getCourseName() + " is gewijzigd");
            saved.showAndWait();
            Main.getSceneManager().showManageCoursesScene();
        }
        return null;
    }

}
