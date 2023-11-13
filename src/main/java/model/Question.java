package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question {
    private final int questionId;
    private final String textQuestion;
    private final String correctAnswer;
    private final String answer2;
    private final String answer3;
    private final String answer4;
    private final Quiz quiz;

    public Question(int questionId, String textQuestion, String correctAnswer, String answer2,
                    String answer3, String answer4, Quiz quiz) {
        this.questionId = questionId;
        this.textQuestion = textQuestion;
        this.correctAnswer = correctAnswer;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.quiz = quiz;
    }

    public Question(String textQuestion, String correctAnswer, String answer2,
                    String answer3, String answer4, Quiz quiz) {
        this(0,textQuestion, correctAnswer,answer2,answer3,answer4,quiz);

    }

    public Question() {
        this(0,"onbekend","onbekend","onbekend",
                "onbekend","onbekend",new Quiz());
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s\n", textQuestion, correctAnswer, answer2,
                answer3, answer4, quiz.getQuizName());
    }
}




