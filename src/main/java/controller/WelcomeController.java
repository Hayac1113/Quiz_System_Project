package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.Course;
import model.Question;
import view.Main;

import static view.Main.getSceneManager;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup(int userId) {
            String user = LoginController.loginUserRole;
            switch (user) {
                case "Student":
                    welcomeLabel.setText("Welkom Student");
                    MenuItem item1 = new MenuItem("Inschrijven/uitschrijven");
                    MenuItem item2 = new MenuItem("Kies een Quiz");
                    taskMenuButton.getItems().add(item1);
                    taskMenuButton.getItems().add(item2);
                    item1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Main.getSceneManager().showStudentSignInOutScene(userId);
                        }
                    });
                    item2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Main.getSceneManager().showSelectQuizForStudent(userId);
                        }
                    });
                    break;
                case "Docent":
                    welcomeLabel.setText("De docent heeft op dit moment geen functionaliteit");
                    break;
                case "Coördinator":
                    welcomeLabel.setText("Welkom Coördinator");
                    MenuItem item5 = new MenuItem("Beheer Quiz");
                    MenuItem item11 = new MenuItem("Beheer Vraag");

                    item5.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
//                            Main.getSceneManager().showManageQuizScene();
                        }
                    });
//                    item11.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent actionEvent) {
//                            Main.getSceneManager().showManageQuestionsScene();
//                        }});

                    MenuItem item6 = new MenuItem("Ga naar dashboard");

                        /*@Override
                        public void handle(ActionEvent actionEvent) {
                            Main.getSceneManager().showCoordinatorDashboard();
                        }*/

                    taskMenuButton.getItems().add(item5);
                    taskMenuButton.getItems().add(item6);
                    taskMenuButton.getItems().add(item11);
                    break;
                case "Administrator":
                    welcomeLabel.setText("Welkom Administrator");
                    Main.getSceneManager().showManageCoursesScene();
//                    MenuItem item7 = new MenuItem("Aanmaken cursussen");
//                    item7.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent actionEvent) {
//                             Main.getSceneManager().showCreateUpdateCourseScene(new Course()); //new Course()); //?
//                        }
//                    });
//                    MenuItem item8 = new MenuItem("Beheer cursussen");
//                    item8.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent actionEvent) {
//                            Main.getSceneManager().showManageCoursesScene();
//                        }
//                    });
//                    MenuItem item9 = new MenuItem("Data exporteren");
//                    taskMenuButton.getItems().add(item7);
//                    taskMenuButton.getItems().add(item8);
//                    taskMenuButton.getItems().add(item9);
                    break;
                case "Functioneel Beheerder":
                    welcomeLabel.setText("Welkom Functioneel Beheerder");
                    break;
            }

        }

        public void doLogout() {
            Main.getSceneManager().showLoginScene();
        }
    }