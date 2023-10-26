package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Create a class Course and the attributes:
public class Course {
    private String courseName;
    private String coordinator;
    private String level;

    // Constructor
    public Course(String courseName, String coordinator, String level) {
        this.courseName = courseName;
        this.coordinator = coordinator;
        this.level = level;
    }

    public Course() {
        this("Unknown", "Unknown", "Unknown");
    }

    // Getters and Setters for courseName
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Getters and Setters for coordinator
    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    // Getters and Setters for level
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    //add TextIO methods
    public static List<Course> loadCSV(String fileName) {
        List<Course> listofCourses = new ArrayList<>();
        File file = new File(String.format("src/main/resources/CSV bestanden/%s", fileName));
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String [] lineArray = input.nextLine().split(",");
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

    @Override
    public String toString() {
        return String.format("%s %s %s\n", courseName, coordinator, level);
    }
}
