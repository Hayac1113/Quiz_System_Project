package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import view.SceneManager;

import static view.Main.getSceneManager;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        String user2 = "Docent";
        switch (user2) {
            case "Student":
                welcomeLabel.setText("Welkom Student");
                MenuItem item1 = new MenuItem("Inschrijven/uitschrijven");
                MenuItem item2 = new MenuItem("Kies een Quiz");
                MenuItem item3 = new MenuItem("Een quiz invullen");
                taskMenuButton.getItems().add(item1);
                taskMenuButton.getItems().add(item2);
                taskMenuButton.getItems().add(item3);
                break;
            case "Docent":
                welcomeLabel.setText("De docent heeft op dit moment geen functionaliteit");
                break;
            case "Coördinator":
                welcomeLabel.setText("Welkom Coördinator");
                MenuItem item5 = new MenuItem("Beheer Quiz");
                MenuItem item6 = new MenuItem("Ga naar dashboard");
                taskMenuButton.getItems().add(item5);
                taskMenuButton.getItems().add(item6);
                break;
            case "Administrator":
                welcomeLabel.setText("Welkom Administrator");
                MenuItem item7 = new MenuItem("Beheer cursussen");
                MenuItem item8 = new MenuItem("Beheer groepen");
                MenuItem item9 = new MenuItem("Data exporteren");
                taskMenuButton.getItems().add(item7);
                taskMenuButton.getItems().add(item8);
                taskMenuButton.getItems().add(item9);
                break;
            case "Functioneel Beheerder":
                welcomeLabel.setText("Welkom Functioneel Beheerder");
                MenuItem item10 = new MenuItem("Beheer gebruiker");
                taskMenuButton.getItems().add(item10);
                break;
        }

    }

    public void doLogout() {
        getSceneManager().showLoginScene();
    }
}
