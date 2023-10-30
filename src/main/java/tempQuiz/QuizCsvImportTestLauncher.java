package tempQuiz;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import model.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizCsvImportTestLauncher {
    public static void main(String[] args) {
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dBaccess);

        List<Quiz> listOfQuizzes = new ArrayList<>();
        File filename = new File("src/main/resources/CSV bestanden/Quizzen.csv");
        try {
            Scanner input = new Scanner(filename);
            while (input.hasNextLine()) {
                String[] lineArray = input.nextLine().split(",");
                String quizName = lineArray[0];
                String level = lineArray[1];
                String succesDefinition = lineArray[2];
                String courseName = lineArray[3];
                listOfQuizzes.add(new Quiz(quizName, level, Integer.parseInt(succesDefinition), courseName));
            }
        } catch (FileNotFoundException notFound) {
            System.out.println("File not found");
        }

        // foreign constraint uit
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("Set FOREIGN_KEY_CHECKS = 0");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Quiz quiz : listOfQuizzes) {
            quizDAO.storeOne(quiz);
        }

        // foreign constraint aan
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("Set FOREIGN_KEY_CHECKS = 1");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dBaccess.closeConnection();
    }

}
