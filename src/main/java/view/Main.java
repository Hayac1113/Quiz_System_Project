package view;

import database.mysql.DBAccess;
import javacouchdb.CouchDBaccess;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {



    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    private static DBAccess dBaccess = null;
    private static CouchDBaccess couchDBaccess = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Make IT Work - Project 1");
        getSceneManager().showLoginScene(); //to loginscreen
//        getSceneManager().showWelcomeScene(); //to test welcomescreen
        primaryStage.show();
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    public static DBAccess getDBaccess() {
        if (dBaccess == null) {
            dBaccess = new DBAccess("QuizMaster","userQuizMaster","userQuizMasterPW");
            dBaccess.openConnection();
        }
        return dBaccess;
    }

    public static CouchDBaccess getCouchDBaccess() {
        if (couchDBaccess == null) {
            couchDBaccess = new CouchDBaccess("quizmaster","admin", "admin");
        }
        return couchDBaccess;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}