package testController;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import javacouchdb.CouchDBaccess;
import model.Course;

import java.util.List;

public class CourseNoSQLLauncher {
    private static CouchDBaccess couchDBaccess;
    private static CourseCouchDBDAO courseCouchDAO;


    public static void main(String[] args) {
        //Access to CouchDB
        couchDBaccess = new CouchDBaccess("quizmaster", "admin", "admin");
        courseCouchDAO = new CourseCouchDBDAO(couchDBaccess);

        //Access to MySQLserver
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dBaccess);

        List<Course> listCourses = courseDAO.getAll();

        if (couchDBaccess.getClient() != null) {
            System.out.println("Connection open");
        }

        for (Course course: listCourses) {
            courseCouchDAO.saveSingleCourse(course);
        }
    }
}
