package model;

public class Quiz {
    private final int quizId;
    private final String quizName;
    private final String level;
    private final int succesDefinition;
    private final Course course;

    public Quiz(int quizId, String quizName, String level, int succesDefinition, Course course) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.level = level;
        this.succesDefinition = succesDefinition;
        this.course = course;
    }

    public Quiz(String quizName, String level, int succesDefinition, Course course) {
        this(0, quizName, level, succesDefinition, course);
    }

    public Quiz() {
        this(0, "Onbekend", "Onbekend", 0, new Course());
    }

    public int getQuizId() {
        return quizId;
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

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %s \n", quizName, level, succesDefinition, course.getCourseName());
    }
}
