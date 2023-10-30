package tempQuestion;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import model.Question;
import model.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionCsvImportTestLauncher {
    public static void main(String[] args) {

        // stap 1: connectie maken
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        dBaccess.openConnection();

        // stap 2: csv file ophalen
        List<Question> questionList = loadCSV("Vragen.csv");
        // Je kunt de lijst printen in console om te controleren
        System.out.println(questionList);

        // stap 3: csv file in de database zetten werkt nog niet door SQL syntax errors
        // for (Question question: questionList){
        // questionDAO.storeOne(question);
        // }

    }

    public static List<Question> loadCSV(String filename) {
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        dBaccess.openConnection();

        List<Question> listOfQuestion = new ArrayList<>();
        File filename2 = new File(String.format("src/main/resources/CSV bestanden/%s", filename));
        QuizDAO quizDAO = new QuizDAO(dBaccess);
        try {
            Scanner input = new Scanner(filename2);
            while (input.hasNextLine()) {
                String[] lineArray = input.nextLine().split(";");
                String textQuestion = lineArray[0];
                String correctAnswer = lineArray[1];
                String answer2 = lineArray[2];
                String answer3 = lineArray[3];
                String answer4 = lineArray[4];
                String quizName = lineArray[5];
                Quiz quiz = quizDAO.getOneByPK(quizName);
                listOfQuestion.add(new Question(textQuestion, correctAnswer, answer2, answer3, answer4, quiz));
            }
        } catch (FileNotFoundException notFound) {
            System.out.println("File not found");
        }
        return listOfQuestion;

    }
}
