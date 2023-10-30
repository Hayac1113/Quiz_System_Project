package database.mysql;

import model.Quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO extends AbstractDAO implements GenericDAO<Quiz, String> {
    public QuizDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Quiz> getAll() {
        List<Quiz> quizList = new ArrayList<>();
        String sql = "SELECT * FROM quiz;";
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String quizName = resultSet.getString("quizName");
                String level = resultSet.getString("level");
                int succesDefinition = resultSet.getInt("succesDefinition");
                String courseName = resultSet.getString("courseName");
                Quiz quiz = new Quiz(quizName, level, succesDefinition, courseName);
                quizList.add(quiz);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quizList;
    }

    @Override
    public Quiz getOneByPK(String PK) {
        Quiz quiz = new Quiz();
        String sql = "SELECT * FROM Quiz WHERE quizName = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, PK);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String quizName = resultSet.getString("quizName");
                String level = resultSet.getString("level");
                int succesDefinition = resultSet.getInt("succesDefinition");
                String courseName = resultSet.getString("courseName");
                quiz = new Quiz(quizName, level, succesDefinition, courseName);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quiz;
    }

    @Override
    public void storeOne(Quiz quiz) {
        String sql = "INSERT INTO Quiz VALUES(?, ?, ?, ?)";
        try {

            setupPreparedStatement(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setString(2, quiz.getLevel());
            preparedStatement.setInt(3, quiz.getSuccesDefinition());
            preparedStatement.setString(4, quiz.getCourseName());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }
}
