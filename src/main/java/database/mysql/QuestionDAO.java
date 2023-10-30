package database.mysql;

import model.Question;
import model.Quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO extends AbstractDAO implements GenericDAO<Question, String> {
    public QuestionDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Question> getAll() {
        List<Question> questionList = new ArrayList<>();
        String sql = "SELECT * FROM question;";
        Question question = null;
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String textQuestion = resultSet.getString("textQuestion");
                String correctAnswer = resultSet.getString("correctAnswer");
                String answer2 = resultSet.getString("answer2");
                String answer3 = resultSet.getString("answer3");
                String answer4 = resultSet.getString("answer4");
                // quizname moet eigenlijk object quiz ophalen, maar tijdelijk zo
                String quizName = resultSet.getString("quizName");
                Quiz quiz = quizDAO.getOneByPK(quizName);
                question = new Question(textQuestion, correctAnswer, answer2, answer3, answer4, quiz);
                questionList.add(question);
            }
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }
        return questionList;
    }

    // onderstaande methode is de reden dat een surrogate key wellicht de moeite
    // waard is voor Question
    @Override
    public Question getOneByPK(String textQuestion) {
        Question question = null;
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        String sql = "SELECT * FROM question WHERE textQuestion = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, textQuestion);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String correctAnswer = resultSet.getString("correctAnswer");
                String answer2 = resultSet.getString("answer2");
                String answer3 = resultSet.getString("answer3");
                String answer4 = resultSet.getString("answer4");
                String quizName = resultSet.getString("quizName");
                // Hier moet nog code komen die het quizobject ophaalt op basis van de quiznaam
                // Maar voor nu nog zonder object
                Quiz quiz = quizDAO.getOneByPK(quizName);
                question = new Question(textQuestion, correctAnswer, answer2, answer3, answer4, quiz);
            }
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }
        return question;
    }

    @Override
    public void storeOne(Question question) {
        String sql = "INSERT INTO question(textQuestion, correctAnswer, answer2, answer3, answer4, quizName)" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, question.getTextQuestion());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.setString(3, question.getAnswer2());
            preparedStatement.setString(4, question.getAnswer3());
            preparedStatement.setString(5, question.getAnswer4());
            preparedStatement.setString(6, question.getQuiz().getQuizName());
            executeManipulateStatement();
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }

    }
}
