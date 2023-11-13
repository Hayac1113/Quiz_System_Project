//package testController;
//
//import database.mysql.CourseDAO;
//import database.mysql.DBAccess;
//import model.Course;
//
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class CourseDAOTestLauncher {
//    public static void main(String[] args) {
//        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
//        dBaccess.openConnection();
//        CourseDAO courseDAO = new CourseDAO(dBaccess);
//
//        //Foreign key constraint uit doen
//        try {
//            Statement stmt = dBaccess.getConnection().createStatement();
//            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
//            stmt.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        courseDAO.storeOne(new Course("TestNaam", "TestLevel", "TestCoordinator"));
//        courseDAO.storeOne(new Course("TestNaam2", "TestLevel", "TestCoordinator"));
//        courseDAO.storeOne(new Course("TestNaam3", "TestLevel", "TestCoordinator"));
//        courseDAO.storeOne(new Course("TestNaam4", "TestLevel", "TestCoordinator"));
//
//        //Foreign key constraint aandoen
//        try {
//            Statement stmt = dBaccess.getConnection().createStatement();
//            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
//            stmt.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(courseDAO.getOneById(5));
//    }
//}
