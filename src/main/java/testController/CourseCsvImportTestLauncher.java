package testController;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import model.Course;
import java.io.File;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseCsvImportTestLauncher {
    public static void main(String[] args) {
        //Verbinding maken met de database
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dBaccess);

        List<Course> listofCourses = new ArrayList<>();
        File file = new File("src/main/resources/CSV bestanden/Cursussen.csv");
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String[] lineArray = input.nextLine().split(",");
                String courseName = lineArray[0];
                String coordinator = lineArray[1];
                String level = lineArray[2];
                listofCourses.add(new Course(courseName, coordinator, level));
            }
        } catch (Exception error) {
            System.out.println("File not found");
        }

        //foreign constraint uit
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("Set FOREIGN_KEY_CHECKS = 0");
            stmt.close();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }

        for (Course course : listofCourses) {
            courseDAO.storeOne(course);
        }

        //foreign constraint aan
        try {
            Statement stmt = dBaccess.getConnection().createStatement();
            stmt.execute("Set FOREIGN_KEY_CHECKS = 1");
            stmt.close();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }

        dBaccess.closeConnection();
    }
}
