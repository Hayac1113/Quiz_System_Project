package model;

import java.time.LocalDateTime;

public class QuizResult implements Comparable<QuizResult> {
    // QuizResult should be uniquely identified by a combination of userId and datetime
    private final int userId;
    private final int quizId;
    private final int quizSuccesDefinition;
    private final String datetime;
    private final int numberOfCorrectAnswers;
    private final int totalNumberOfQuestions;

    public QuizResult(int userId, int quizId, int quizSuccesDefinition, String datetime, int numberOfCorrectAnswers, int totalNumberOfQuestions) {
        this.userId = userId;
        this.quizId = quizId;
        this.quizSuccesDefinition = quizSuccesDefinition;
        this.datetime = datetime;
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

    @Override
    public int compareTo(QuizResult otherQuizResult) {
        return datetime.compareTo(otherQuizResult.datetime);
    }

    public int getUserId() {
        return userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public int getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

}
