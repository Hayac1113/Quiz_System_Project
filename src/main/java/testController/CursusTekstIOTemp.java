package testController;

import model.Course;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CursusTekstIOTemp {
    public static List<Course> loadCSV(String fileName) {
        List<Course> listofCourses = new ArrayList<>();
        File file = new File(String.format("src/main/resources/CSV bestanden/%s", fileName));
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String[] lineArray = input.nextLine().split(",");
                String courseName = lineArray[0];
                String coordinator = lineArray[1];
                String level = lineArray[2];
                listofCourses.add(new Course(courseName, coordinator, level));
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        return listofCourses;
    }
}
