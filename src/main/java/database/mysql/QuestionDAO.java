package database.mysql;

import model.Course;
import model.Question;
import model.Quiz;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO extends AbstractDAO implements GenericDAO<Question> {
    public QuestionDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Question> getAll() {
        List<Question> questionList = new ArrayList<>();
        String sql = "SELECT * FROM question;";
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String textQuestion = resultSet.getString("textQuestion");
                String correctAnswer = resultSet.getString("correctAnswer");
                String answer2 = resultSet.getString("answer2");
                String answer3 = resultSet.getString("answer3");
                String answer4 = resultSet.getString("answer4");
                int quizId = resultSet.getInt("quiz");
                Quiz quiz = quizDAO.getOneById(quizId);
                Question question = new Question(questionId, textQuestion, correctAnswer, answer2,
                        answer3, answer4, quiz);
                questionList.add(question);
            }
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }
        return questionList;
    }

    @Override
    public Question getOneById(int id) {
        Question question = null;
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        String sql = "SELECT * FROM question WHERE questionId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String textQuestion = resultSet.getString("textQuestion");
                String correctAnswer = resultSet.getString("correctAnswer");
                String answer2 = resultSet.getString("answer2");
                String answer3 = resultSet.getString("answer3");
                String answer4 = resultSet.getString("answer4");
                int quizId = resultSet.getInt("quiz");
                Quiz quiz = quizDAO.getOneById(quizId);
                question = new Question(id, textQuestion, correctAnswer, answer2,
                        answer3, answer4, quiz);
            }
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }
        return question;
    }


    @Override
    public void storeOne(Question question) {

        String sql = "INSERT INTO question(textQuestion, correctAnswer, answer2, answer3, answer4, quiz)" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1, question.getTextQuestion());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.setString(3, question.getAnswer2());
            preparedStatement.setString(4, question.getAnswer3());
            preparedStatement.setString(5, question.getAnswer4());
            preparedStatement.setInt(6, question.getQuiz().getQuizId());
            executeManipulateStatement();
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }
    }

    @Override
    public void updateOne(Question question) {
        String sql = "UPDATE Question SET textQuestion = ?, correctAnswer = ?, answer2 = ?," +
                " answer3 = ?, answer4 = ?, quiz = ? WHERE questionId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, question.getTextQuestion());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.setString(3, question.getAnswer2());
            preparedStatement.setString(4, question.getAnswer3());
            preparedStatement.setString(5, question.getAnswer4());
            preparedStatement.setInt(6, question.getQuiz().getQuizId());
            preparedStatement.setInt(7, question.getQuestionId());

            executeManipulateStatement();
        } catch (SQLException sqlFout) {
            System.out.println("SQL fout: " + sqlFout.getMessage());
        }
    }

    public void deleteOne(Question question) {
        String sql = "Delete from question WHERE questionId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, question.getQuestionId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println("SQL fout: " + sqlerror.getMessage());
        }
    }

    public Quiz getQuizByName(String quizNameCSV) {
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        String sql = "SELECT * FROM quiz WHERE quizName = ?";
        Quiz quiz = null;
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, quizNameCSV);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String level = resultSet.getString("level");
                int succesDefinition = resultSet.getInt("succesDefinition");
                int courseId = resultSet.getInt("course");
                Course course = courseDAO.getOneById(courseId);
                quiz = new Quiz(quizId, quizNameCSV, level, succesDefinition, course);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quiz;
    }

    public int getNumberOfQuestions(int quizId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM question WHERE quiz =?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                count = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return count;
    }

    public List<Question> getQuestionsByQuizId(int id) {
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        List<Question> quizQuestionList = new ArrayList<>();
        Question question = null;
        String sql = "SELECT * FROM Question WHERE quiz = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String textQuestion = resultSet.getString("textQuestion");
                String correctAnswer = resultSet.getString("correctAnswer");
                String answer2 = resultSet.getString("answer2");
                String answer3 = resultSet.getString("answer3");
                String answer4 = resultSet.getString("answer4");
                Quiz activeQuiz = quizDAO.getOneById(id);
                question = new Question(questionId, textQuestion, correctAnswer, answer2, answer3, answer4, activeQuiz);
                quizQuestionList.add(question);

            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quizQuestionList;
    }
}
