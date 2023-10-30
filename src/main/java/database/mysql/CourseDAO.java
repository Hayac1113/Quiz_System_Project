package database.mysql;

import model.Course;
import testController.CursusTekstIOTemp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends AbstractDAO implements GenericDAO<Course, String> {

    public CourseDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT courseName, coordinator, level FROM Course";
        List<Course> courseList = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String courseName = resultSet.getString("courseName");
                String coordinator = resultSet.getString("coordinator");
                String level = resultSet.getString("level");
                Course course = new Course(courseName, coordinator, level);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    @Override
    public Course getOneByPK(String PK) {
        return null;
    }

    @Override
    public void storeOne(Course course) {
        String sql = "INSERT INTO Course (courseName, coordinator, level) VALUES (?, ?, ?)";
        try {
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCoordinator());
            preparedStatement.setString(3, course.getLevel());
            executeInsertStatementWithKey();
        } catch (SQLException foutmelding) {
            System.out.println(foutmelding.getMessage());
        }
    }

    public void insertCursusFromCSVToDB() {
        List<Course> courseList = CursusTekstIOTemp.loadCSV("Cursussen.csv");
        for (Course course : courseList) {
            storeOne(course);
        }
    }
}
