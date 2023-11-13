package controller;

import database.mysql.UserDAO;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import model.User;
import view.Main;

import java.util.List;
import static java.util.Objects.isNull;
import static view.Main.getSceneManager;

public class ManageUsersController {

    private final UserDAO userDAO;

    @FXML
    ListView<User> userList;

    @FXML
    private Label roleCounter;

    public ManageUsersController() {
        this.userDAO = new UserDAO(Main.getDBaccess());
    }

    public void setup() {
        List<User> users = userDAO.getAll();
        userList.getItems().addAll(users);
        userList.setStyle("-fx-font-family: \"Monospaced\"; -fx-font-weight: bold");
    }

    public void doUpdateRoleCounter() {
        User user = userList.getSelectionModel().getSelectedItem();
        int count = userDAO.countByRole(user.getRole());
        String rolMeervoud = "";
        switch (user.getRole()) {
            case "Student":
                rolMeervoud = "studenten";
                break;
            case "Coördinator":
                rolMeervoud = "coördinatoren";
                break;
            case "Administrator":
                rolMeervoud = "administratoren";
                break;
            case "Functioneel Beheerder":
                rolMeervoud = "functioneel beheerders";
                break;
            case "Docent":
                rolMeervoud = "docenten";
                break;
        }
        roleCounter.setText(String.format("Er zijn %d %s.", count, rolMeervoud));
    }
    public void doMenu() {
        Main.getSceneManager().showLoginScene();
    }

    public void doCreateUser() {
        Main.getSceneManager().showCreateUpdateUserScene(new User());
    }

    public void doUpdateUser() {
        User user = userList.getSelectionModel().getSelectedItem();
        if (isNull(user)) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setTitle("Quizmaster");
            foutmelding.setHeaderText("Selecteer een gebruiker om deze te wijzigen. ");
            foutmelding.show();
        } else {
            Main.getSceneManager().showCreateUpdateUserScene(user);
        }
    }

    public void doDeleteUser() {
        User user = userList.getSelectionModel().getSelectedItem();
        userDAO.deleteOne(user);
        // Informatievenster tonen dat verwijderen gebruiker is gelukt.
        Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
        opgeslagen.setTitle("Quizmaster");
        opgeslagen.setHeaderText(String.format("%s %s is verwijderd.", user.getRole(), user.getUserName()));
        opgeslagen.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        opgeslagen.show();
        // Sluit Informatievenster automatisch na 1 seconde.
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            opgeslagen.close();
            // Update Userbeheerscherm.
            getSceneManager().showManageUserScene();
        });
        delay.play();
    }
}
