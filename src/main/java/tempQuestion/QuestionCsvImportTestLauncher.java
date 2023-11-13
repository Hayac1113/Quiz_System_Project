package tempQuestion;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import model.Question;
import model.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionCsvImportTestLauncher {
    public static void main(String[] args) {

        DBAccess dBaccess = new DBAccess("QuizMaster","userQuizMaster",
                "userQuizMasterPW");
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        dBaccess.openConnection();


        List<Question> questionList = new ArrayList<>();
        File filename = new File("src/main/resources/CSV bestanden/Vragen.csv");
        try {
            Scanner input = new Scanner(filename);
            while (input.hasNextLine()){
                String [] lineArray = input.nextLine().split(";");
                String textQuestion = lineArray[0];
                String correctAnswer = lineArray[1];
                String answer2 = lineArray[2];
                String answer3 = lineArray[3];
                String answer4 = lineArray[4];
                String quizName = lineArray[5];
                Quiz quiz = questionDAO.getQuizByName(quizName);
                questionList.add(new Question(textQuestion, correctAnswer, answer2, answer3, answer4, quiz));


            }
        } catch (FileNotFoundException notFound){
            System.out.println("File not found");
        }

        for (Question question : questionList){
            questionDAO.storeOne(question);
        }

        for (Question question : questionList){
            System.out.println(question);
        }

        dBaccess.closeConnection();

    }
}
