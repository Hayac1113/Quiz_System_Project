package controller;

import model.Course;
import model.Question;
import testController.CursusTekstIOTemp;

import java.util.List;

public class TekstIOTestLauncher {
    public static void main(String[] args) {
        List<Question> questionList = Question.loadCSV("Vragen.csv");
        System.out.println(questionList);

        List<Course> cursusList = CursusTekstIOTemp.loadCSV("Cursussen.csv");
        System.out.println(cursusList);
    }


}
