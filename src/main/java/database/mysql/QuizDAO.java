package database.mysql;

import model.Course;
import model.Quiz;
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO extends AbstractDAO implements GenericDAO<Quiz> {

    public QuizDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Quiz> getAll() {
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        List<Quiz> quizList = new ArrayList<>();
        String sql = "SELECT * FROM quiz;";
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String quizName = resultSet.getString("quizName");
                String level = resultSet.getString("level");
                int succesDefinition = resultSet.getInt("succesDefinition");
                int courseId = resultSet.getInt("course");
                Course course = courseDAO.getOneById(courseId);
                Quiz quiz = new Quiz(quizId, quizName, level, succesDefinition, course);
                quizList.add(quiz);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quizList;
    }

    @Override
    public Quiz getOneById(int id) {
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        Quiz quiz = null;
        String sql = "SELECT * FROM Quiz WHERE quizId = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String quizName = resultSet.getString("quizName");
                String level = resultSet.getString("level");
                int succesDefinition = resultSet.getInt("succesDefinition");
                int courseId = resultSet.getInt("course");
                Course course = courseDAO.getOneById(courseId);
                quiz = new Quiz(id, quizName, level, succesDefinition, course);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quiz;
    }

    @Override
    public void storeOne(Quiz quiz) {
        String sql = "INSERT INTO quiz(quizName, level, succesDefinition, course) VALUES(?, ?, ?, ?)";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setString(2, quiz.getLevel());
            preparedStatement.setInt(3, quiz.getSuccesDefinition());
            preparedStatement.setInt(4, quiz.getCourse().getCourseId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }

    @Override
    public void updateOne(Quiz quiz) {
        String sql = "UPDATE quiz SET quizName = ?, level = ?, succesDefinition = ?, course = ? WHERE quizId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setString(2, quiz.getLevel());
            preparedStatement.setInt(3, quiz.getSuccesDefinition());
            preparedStatement.setInt(4, quiz.getCourse().getCourseId());
            preparedStatement.setInt(5, quiz.getQuizId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }

    public void deleteOne(Quiz quiz){
        String sql = "DELETE FROM quiz WHERE quizId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, quiz.getQuizId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }

    public Course getCourseByName(String courseNameCSV) {
        UserDAO userDAO = new UserDAO(dbAccess);
        String sql = "SELECT * FROM course WHERE courseName = ?";
        Course course = null;
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, courseNameCSV);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                String level = resultSet.getString("level");
                int coordinatorId = resultSet.getInt("coordinator");
                User coordinator = userDAO.getOneById(coordinatorId);
                course = new Course(courseId, courseNameCSV, level, coordinator);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return course;
    }

    public List<Quiz> getQuizzesByCourseId(int id) {
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        List<Quiz> quizListByCourse = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE course = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String quizName = resultSet.getString("quizName");
                String level = resultSet.getString("level");
                int succesDefinition = resultSet.getInt("succesDefinition");
                Course course = courseDAO.getOneById(id);
                Quiz quiz = new Quiz(quizId, quizName, level, succesDefinition, course);
                quizListByCourse.add(quiz);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return quizListByCourse;
    }

    public boolean findByName(String name) {
        boolean found = false;
        String sql = "SELECT * FROM quiz WHERE quizName = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                String quizName = resultSet.getString("quizName");
                if (quizName.equals(name)){
                    found = true;
                }
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return found;
    }

}
