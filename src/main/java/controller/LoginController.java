package controller;

import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.User;
import view.Main;

import static java.util.Objects.isNull;
import static view.Main.getSceneManager;

public class LoginController {

    private final UserDAO userDAO;
    public static String loginUserRole;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public LoginController() {
        this.userDAO = new UserDAO(Main.getDBaccess());
    }

    public void doLogin() {

        // Ingevoerd gebruikersnaam en wachtwoord inlezen
        String userName = nameTextField.getText();
        String password = passwordField.getText();
        // Controleren of de gebruiker het gebruikersnaam en wachtwoord wel heeft ingevuld
        if (nameTextField.getText().isEmpty()) {
            showErrorDialog("Onjuiste gebruikersnaam", "Voer een gebruikersnaam in.");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showErrorDialog("Ongeld wachtwoord", "Voer een wachtwoord in.");
            return;
        }

        // User ophalen uit database op basis van userName
        User loginUser = userDAO.getUserByName(userName);
        if (!isNull(loginUser)) {
            // Wachtwoord controleren
            if (loginUser.getPassword().equals(password)) {
                // Gebruikersrol initialiseren (public)
                loginUserRole = loginUser.getRole();
                int loginUserId = loginUser.getUserId();
                //Doorgaan naar scherm op basis van rol
                switch (loginUserRole) {
                    case "Student":
                        getSceneManager().showWelcomeScene(loginUserId);
                        break;
                    case "Docent":
                        showErrorDialog("De docent heeft op dit moment geen functionaliteit", "");
                        break;
                    case "Coördinator":
                        getSceneManager().showCoordinatorDashboard(loginUserId);
                        break;
                    case "Administrator":
                         getSceneManager().showManageCoursesScene();
                        break;
                    case "Functioneel Beheerder":
                        getSceneManager().showManageUserScene();
                }
            }
            else {
                showErrorDialog("Ongeldig gebruikersnaam en/of wachtwoord", "Voer nogmaals de gebruikersnaam of het wachtwoord in.");
            }
        } else {
            showErrorDialog("Ongeldig gebruikersnaam en/of wachtwoord", "Voer nogmaals de gebruikersnaam of het wachtwoord in.");
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Quizmaster");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);  // Use INFORMATION type for success messages
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void doQuit() {
        Main.getDBaccess().closeConnection();
        System.exit(0);
    }
}
