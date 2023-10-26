package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private String quizName;
    private String level;
    private int succesDefinition;
    private String courseName;

    public Quiz(String quizName, String level, int succesDefinition, String courseName) {
        this.quizName = quizName;
        this.level = level;
        this.succesDefinition = succesDefinition;
        this.courseName = courseName;
    }

    public Quiz() {
        this("Onbekend", "Onbekend", 0, "Onbekend");
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setSuccesDefinition(int succesDefinition) {
        this.succesDefinition = succesDefinition;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getLevel() {
        return level;
    }

    public int getSuccesDefinition() {
        return succesDefinition;
    }

    public String getCourseName() {
        return courseName;
    }
    public static List<Quiz> loadCSV(String filename) {
        List<Quiz> listOfQuizzes = new ArrayList<>();
        File filename2 = new File(String.format("src/main/resources/CSV bestanden/%s", filename));
        try {
            Scanner input = new Scanner(filename2);
            while (input.hasNextLine()){
                String [] lineArray = input.nextLine().split(",");
                String quizName = lineArray[0];
                String level = lineArray[1];
                String succesDefinition = lineArray[2];
                String courseName = lineArray[3];
                listOfQuizzes.add(new Quiz(quizName, level, Integer.parseInt(succesDefinition), courseName));
            }
        } catch (FileNotFoundException notFound){
            System.out.println("File not found");
        }
        return listOfQuizzes;

    }
    @Override
    public String toString() {
        return String.format("%s %s %d %s \n", quizName, level, succesDefinition, courseName);
    }
}
