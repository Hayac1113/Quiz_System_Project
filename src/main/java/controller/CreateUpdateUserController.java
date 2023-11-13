package controller;

import database.mysql.UserDAO;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static view.Main.getSceneManager;

public class CreateUpdateUserController {
    private final UserDAO userDAO;
    private final String VIEWTITLE = "Quizmaster";

    @FXML
    private Label titelLabel;

    @FXML
    private TextField GebruikersnummerTextfield;

    @FXML
    private TextField GebruikersnaamTextfield;

    @FXML
    private TextField WachtwoordTextfield;

    @FXML
    private TextField VoornaamTextfield;

    @FXML
    private TextField TussenvoegselTextfield;

    @FXML
    private TextField AchternaamTextfield;

    @FXML
    private ComboBox<String> RolDropmenu;

    public CreateUpdateUserController() {
        this.userDAO = new UserDAO(Main.getDBaccess());
    }

    public void setup(User user) {
        GebruikersnummerTextfield.setVisible(false);
        List<String> rollen = new ArrayList<>();
        Collections.addAll(rollen,"Student", "Docent", "Coördinator", "Administrator", "Functioneel Beheerder");
        RolDropmenu.getItems().addAll(rollen);
        if (user.getUserId() != 0) {
            titelLabel.setText("Wijzig gebruiker");
            GebruikersnummerTextfield.setText(String.valueOf(user.getUserId()));
            GebruikersnaamTextfield.setText(user.getUserName());
            WachtwoordTextfield.setText(user.getPassword());
            VoornaamTextfield.setText(user.getFirstName());
            TussenvoegselTextfield.setText(user.getInfix());
            AchternaamTextfield.setText(user.getLastName());
            RolDropmenu.getSelectionModel().select(user.getRole());
        } else {
            GebruikersnummerTextfield.setText(String.valueOf(user.getUserId()));
        }
    }

    @FXML
    public void doCreateUpdateUser(ActionEvent actionEvent) {
        User user = createUser();
        User userCheck = userDAO.getUserByName(GebruikersnaamTextfield.getText());
        if (user != null) {
            if ((Integer.parseInt(GebruikersnummerTextfield.getText()) == 0) && isNull(userCheck)) {
                // Nieuwe gebruiker opslaan in database
                userDAO.storeOne(user);
                // Informatievenster tonen dat opslaan nieuwe gebruiker is gelukt.
                Alert opgeslagen = makeNotification(user, "opgeslagen");
                // Sluit Informatievenster automatisch na 1 seconde.
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(e -> {
                    opgeslagen.close();
                    // Maak invoervelden leeg.
                    getSceneManager().showCreateUpdateUserScene(new User());
                });
                delay.play();
            } else if ((Integer.parseInt(GebruikersnummerTextfield.getText()) != 0) && (isNull(userCheck) || (Integer.parseInt(GebruikersnummerTextfield.getText()) == userCheck.getUserId()))) {
                int id = Integer.parseInt(GebruikersnummerTextfield.getText());
                user.setUserId(id);
                userDAO.updateOne(user);
                // Informatievenster tonen dat opslaan gewijzigde gebruiker is gelukt.
                Alert opgeslagen = makeNotification(user, "gewijzigd");
                // Sluit Informatievenster automatisch na 1 seconde.
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(e -> {
                    opgeslagen.close();
                    // Maak invoervelden leeg.
                    getSceneManager().showManageUserScene();
                });
                delay.play();
                //
            } else {
                Alert foutmelding = new Alert(Alert.AlertType.ERROR);
                foutmelding.setTitle(VIEWTITLE);
                foutmelding.setHeaderText("Gebruikersnaam bestaat al.");
                foutmelding.setContentText("Kies een andere gebruikersnaam.");
                foutmelding.show();
            }
        }
    }

    public Alert makeNotification(User user, String action) {
        Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
        opgeslagen.setTitle(VIEWTITLE);
        opgeslagen.setHeaderText(String.format("%s %s is %s.", user.getRole(), user.getUserName(), action));
        opgeslagen.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        opgeslagen.show();
        return opgeslagen;
    }

    @FXML
    public void doMenu(ActionEvent actionEvent) {
        Main.getSceneManager().showManageUserScene();
    }

    private User createUser() {
        StringBuilder waarschuwing = new StringBuilder();
        boolean correcteInvoer = true;
        String gebruikersnaam = GebruikersnaamTextfield.getText();
        String wachtwoord = WachtwoordTextfield.getText();
        String voornaam = VoornaamTextfield.getText();
        String tussenvoegsel = TussenvoegselTextfield.getText();
        String achternaam = AchternaamTextfield.getText();
        String rol = RolDropmenu.getValue();

        if (gebruikersnaam.isEmpty()) {
            waarschuwing.append("Gebruikersnaam ontbreekt. Vul een gebruikersnaam in.\n");
            correcteInvoer = false;
        }

        if (wachtwoord.isEmpty()) {
            waarschuwing.append("Wachtwoord ontbreekt. Vul een wachtwoord in.\n");
            correcteInvoer = false;
        }

        if (voornaam.isEmpty()) {
            waarschuwing.append("Voornaam ontbreekt. Vul een voornaam in.\n");
            correcteInvoer = false;
        }

        if (achternaam.isEmpty()) {
            waarschuwing.append("Achternaam ontbreekt. Vul een achternaam in.\n");
            correcteInvoer = false;
        }

        if (!isNull(rol) && rol.isEmpty()) {
            waarschuwing.append("Rol ontbreekt. Vul een rol in.\n");
            correcteInvoer = false;
        } else if (isNull(rol)) {
            waarschuwing.append("Rol ontbreekt. Vul een rol in.\n");
            correcteInvoer = false;
        }

        if (!correcteInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setTitle(VIEWTITLE);
            foutmelding.setHeaderText("Opslaan niet mogelijk.");
            foutmelding.setContentText(waarschuwing.toString());
            foutmelding.show();
            return null;
        } else {
            return new User(gebruikersnaam, wachtwoord, voornaam, tussenvoegsel, achternaam, rol);
        }
    }
}
