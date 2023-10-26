package controller;

import model.Cursus;
import model.Question;
import testController.CursusTekstIOTemp;

import java.util.List;

public class TekstIOTestLauncher {
    public static void main(String[] args) {
        List<Question> questionList = Question.loadCSV("Vragen.csv");
        System.out.println(questionList);

        List<Cursus> cursusList = CursusTekstIOTemp.loadCSV("Cursussen.csv");
        System.out.println(cursusList);
    }


}
