package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static view.Main.getSceneManager;

public class LoginController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public void doLogin() {
        String userName = nameTextField.getText();
        String password = passwordField.getText();

        if (nameTextField.getText().isEmpty()) {
            showErrorDialog("Onjuiste gebruikersnaam", "Voer een gebruikersnaam in.");
            return;
        }

        if (passwordField.getText().isEmpty()) {
            showErrorDialog("Ongeld wachtwoord", "Voer een wachtwoord in.");
            return;
        }

        if (!"a1".equals(userName) || !"a1".equals(password)) {
            showErrorDialog("Ongeldig gebruikersnaam en/of wachtwoord", "Voer nogmaals de gebruikersnaam of het wachtwoord in.");
        } else {
            showSuccessDialog("Login succesvol", "Login succesvol!");
            getSceneManager().showWelcomeScene();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
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
        System.exit(0);
    }
}
