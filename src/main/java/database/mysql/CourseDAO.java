package database.mysql;

import model.Course;
import model.Quiz;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CourseDAO extends AbstractDAO implements GenericDAO<Course> {

    public CourseDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Course> getAll() {
        UserDAO userDAO = new UserDAO(dbAccess);
        String sql = "SELECT * FROM Course";
        List<Course> courseList = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                String courseName = resultSet.getString("courseName");
                String level = resultSet.getString("level");
                int coordinator = resultSet.getInt("coordinator");
                User user = userDAO.getOneById(coordinator);
                Course course = new Course(courseId, courseName, level, user);
                courseList.add(course);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return courseList;
    }

    @Override
    public Course getOneById(int id) {
        UserDAO userDAO = new UserDAO(dbAccess);
        Course course = null;
        String sql = "SELECT * FROM Course WHERE courseId = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String courseName = resultSet.getString("courseName");
                String level = resultSet.getString("level");
                int coordinator = resultSet.getInt("coordinator");
                User user = userDAO.getOneById(coordinator);
                course = new Course(id, courseName, level, user);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return course;
    }

    @Override
    public void storeOne(Course course) {
        String sql = "INSERT INTO Course(courseName, level, coordinator) VALUES (?, ?, ?)";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getLevel());
            preparedStatement.setInt(3, course.getCoordinator().getUserId());
            executeManipulateStatement();
        } catch (SQLException foutmelding) {
            System.out.println(foutmelding.getMessage());
        }
    }

    @Override
    public void updateOne (Course course) {
        String sql = "UPDATE Course SET courseName = ?, level = ?, coordinator = ? WHERE courseId = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getLevel());
            preparedStatement.setInt(3, course.getCoordinator().getUserId());
            preparedStatement.setInt(4, course.getCourseId());
            executeManipulateStatement();
        } catch (SQLException foutmelding) {
            System.out.println(foutmelding.getMessage());
        }
    }

    public void deleteOne(Course course){
        String sql = "DELETE FROM course WHERE courseId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, course.getCourseId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }

    public Course findByName(String courseName) {
        UserDAO userDAO = new UserDAO(dbAccess);
        Course course = null;
        String sql = "SELECT * FROM Course WHERE courseName = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, courseName);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                String level = resultSet.getString("level");
                int coordinatorId = resultSet.getInt("coordinator");
                User user = userDAO.getOneById(coordinatorId);
                course = new Course(courseId, courseName, level, user);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return course;
    }
    public List<Course> getCoursesByUserId(int id) {
        UserDAO userDAO = new UserDAO(dbAccess);
        List<Course> coordinatorCourseList = new ArrayList<>();
        Course course = null;
        String sql = "SELECT * FROM Course WHERE coordinator = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                String courseName = resultSet.getString("courseName");
                String level = resultSet.getString("level");
                int coordinator = resultSet.getInt("coordinator");
                User user = userDAO.getOneById(coordinator);
                course = new Course(courseId, courseName, level, user);
                coordinatorCourseList.add(course);

            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return coordinatorCourseList;
    }

    public void storeOneStudentRegistration(Course course, User student) {
        String sql = "INSERT INTO studentRegistration(student, course) VALUES(?, ?);";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, student.getUserId());
            preparedStatement.setInt(2, course.getCourseId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
    }

    public void deleteOneStudentRegistration(Course course, User student) {
        String sql = "DELETE FROM studentRegistration WHERE course = ? AND student = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, course.getCourseId());
            preparedStatement.setInt(2, student.getUserId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }

    public List<Course> getCoursesByStudent(int userId) {
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        List<Course> listOfCourses = new ArrayList<>();
        String sql = "SELECT course FROM StudentRegistration WHERE student = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("course");
                Course course = courseDAO.getOneById(courseId);
                listOfCourses.add(course);
            }
        } catch (SQLException foutmelding) {
            System.out.println(foutmelding.getMessage());
        }
        return listOfCourses;
    }

    public int countStudentsForCourse(int courseId) {
        String sql = "SELECT COUNT(*) AS student_count FROM studentRegistration WHERE course = ?";
        int count = 0;
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                count = resultSet.getInt("student_count");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    //Methode aanroepen om een lijst van alle cursussen te printen naar een txt bestand
    public void createCourseReport(String filename) {
        List<Course> courses = getAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : courses) {
                int studentCount = countStudentsForCourse(course.getCourseId());
                writer.write(course.getCourseName() + ": " + studentCount + " ingeschreven student(en)");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Er is een fout opgetreden tijdens het schrijven van het bestand: " + e.getMessage());
        }
    }
}
