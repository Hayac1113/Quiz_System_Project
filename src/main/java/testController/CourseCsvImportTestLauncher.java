package testController;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import model.Course;
import model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseCsvImportTestLauncher {
    public static void main(String[] args) {
        //Verbinding maken met de database
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        UserDAO userDAO = new UserDAO(dBaccess);

        List<Course> listofCourses = new ArrayList<>();
        File file = new File("src/main/resources/CSV bestanden/Cursussen.csv");
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String[] lineArray = input.nextLine().split(",");
                String courseName = lineArray[0];
                String level = lineArray[1];
                String coordinator = lineArray[2];
                User coordinator2 = userDAO.getUserByName(coordinator);
                listofCourses.add(new Course(courseName, level, coordinator2));
            }
        } catch (Exception error) {
            System.out.println("File not found");
        }

        for (Course course : listofCourses) {
            courseDAO.storeOne(course);
        }

        dBaccess.closeConnection();
    }
}
