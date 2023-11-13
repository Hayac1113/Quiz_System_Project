package controller;

import database.mysql.CourseDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.Course;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class StudentSignInOutController {

    private final UserDAO userDAO;
    private final CourseDAO courseDAO;
    private int loginUserId;
    @FXML
    private ListView<Course> signedOutCourseList;
    @FXML
    private ListView <Course> signedInCourseList;

    public StudentSignInOutController() {
        this.userDAO = new UserDAO(Main.getDBaccess());
        this.courseDAO = new CourseDAO(Main.getDBaccess());
    }

    public void setup(int userId) {
        loginUserId = userId;

        signedOutCourseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        signedInCourseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<Course> signedInCourses = courseDAO.getCoursesByStudent(userId);
        signedInCourseList.getItems().addAll(signedInCourses);

        List <Course> signedOutCourses = courseDAO.getAll();
        signedOutCourses.removeAll(signedInCourses);
        signedOutCourseList.getItems().addAll(signedOutCourses);
    }

    @FXML
    public void doMenu(ActionEvent actionEvent) {
        Main.getSceneManager().showWelcomeScene(loginUserId);
    }

    public void doSignIn() {
        List<Course> coursesToAdd = new ArrayList<>(signedOutCourseList.getSelectionModel().getSelectedItems());
        for (Course courseToAdd : coursesToAdd) {
            if (courseToAdd != null) {
                signedOutCourseList.getItems().remove(courseToAdd);
                signedInCourseList.getItems().add(courseToAdd);
                courseDAO.storeOneStudentRegistration(courseToAdd, userDAO.getOneById(loginUserId));
            }
        }
    }

    public void doSignOut() {
        List<Course> coursesToRemove = new ArrayList<>(signedInCourseList.getSelectionModel().getSelectedItems());
        for(Course courseToRemove : coursesToRemove) {
            if (courseToRemove != null) {
                signedInCourseList.getItems().remove(courseToRemove);
                signedOutCourseList.getItems().add(courseToRemove);
                courseDAO.deleteOneStudentRegistration(courseToRemove, userDAO.getOneById(loginUserId));
            }
        }
    }
}
