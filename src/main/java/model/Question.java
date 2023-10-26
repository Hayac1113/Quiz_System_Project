package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question {

    private String textQuestion;
    private String correctAnswer;
    private String answer2;
    private String answer3;
    private String answer4;
    private String quizName;

    public Question(String textQuestion, String correctAnswer, String answer2,
                    String answer3, String answer4, String quizName) {
        this.textQuestion = textQuestion;
        this.correctAnswer = correctAnswer;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.quizName = quizName;
    }
    public Question(){
        this("Onbekend", "Onbekend", "Onbekend",
                "Onbekend", "Onbekend", "Onbekend");
    }
    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public static List<Question> loadCSV(String filename) {
        List<Question> listOfQuestion = new ArrayList<>();
        File filename2 = new File(String.format("src/main/resources/CSV bestanden/%s", filename));
        try {
            Scanner input = new Scanner(filename2);
            while (input.hasNextLine()){
                String [] lineArray = input.nextLine().split(";");
                String textQuestion = lineArray[0];
                String correctAnswer = lineArray[1];
                String answer2 = lineArray[2];
                String answer3 = lineArray[3];
                String answer4 = lineArray[4];
                String quizName = lineArray[5];
                listOfQuestion.add(new Question(textQuestion, correctAnswer, answer2, answer3, answer4, quizName));
            }
        } catch (FileNotFoundException notFound){
            System.out.println("File not found");
        }
        return listOfQuestion;

    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s\n", textQuestion, correctAnswer, answer2, answer3, answer4, quizName);
    }
}




