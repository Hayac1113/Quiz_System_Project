package controller;

import model.Question;

import java.util.List;

public class TekstIOTestLauncher {
    public static void main(String[] args) {
        List<Question> questionList = Question.loadCSV("Vragen.csv");
        System.out.println(questionList);


    }


}
