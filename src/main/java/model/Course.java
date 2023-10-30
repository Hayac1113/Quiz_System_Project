package model;

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

    @Override
    public String toString() {
        return String.format("%s %s %s\n", courseName, coordinator, level);
    }
}
