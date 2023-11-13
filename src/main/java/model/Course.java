package model;

//Aanmaken van de klasse Course en declareren van de variabelen
public class Course {
    private final int courseId;
    private final String courseName;
    private final String level;
    private final User coordinator;

    public Course(int courseId, String courseName, String level, User coordinator) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.level = level;
        this.coordinator = coordinator;
    }

    public Course(String courseName, String level, User coordinator) {
        this(0, courseName, level, coordinator);
    }

    public Course() {
        this(0, "Onbekend", "Onbekend", new User());
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getLevel() {
        return level;
    }

    public User getCoordinator() {
        return coordinator;
    }

    @Override
    public String toString() {
        return String.format("%s", courseName);
    }
}
