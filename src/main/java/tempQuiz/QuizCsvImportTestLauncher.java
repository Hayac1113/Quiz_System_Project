package tempQuiz;


import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import model.Course;
import model.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class QuizCsvImportTestLauncher {
    public static void main(String[] args) {
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dBaccess);

//        List<Quiz> listOfQuizzes = new ArrayList<>();
        File filename = new File("src/main/resources/CSV bestanden/Quizzen.csv");
        try {
            Scanner input = new Scanner(filename);
            while (input.hasNextLine()) {
                String[] lineArray = input.nextLine().split(",");
                String quizName = lineArray[0];
                String level = lineArray[1];
                String succesDefinition = lineArray[2];
                String courseName = lineArray[3];
                Course course = quizDAO.getCourseByName(courseName);
                quizDAO.storeOne(new Quiz(quizName, level, Integer.parseInt(succesDefinition), course));
            }
        } catch (FileNotFoundException notFound) {
            System.out.println("File not found");
        }

        List<Quiz> quizList = quizDAO.getAll();

        for (Quiz quiz : quizList) {
            System.out.println(quiz);
        }

        dBaccess.closeConnection();
    }

}
