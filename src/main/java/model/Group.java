package model;

public class Group {

    private Course courseName;
    private String groupName;
    private int maxNumberStudents;
    private User teacher;

    public Group(Course courseName, String groupName, int maxNumberStudent, User teacher) {
        this.courseName = courseName;
        this.groupName = groupName;
        this.maxNumberStudents = maxNumberStudent;
        this.teacher = teacher;
    }

    public Group() {
        this(new Course(), "Onbekend", 0, new User());
    }

    public Course getCourseName() {
        return courseName;
    }

    public void setCourseName(Course courseName) {
        this.courseName = courseName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMaxNumberStudents() {
        return maxNumberStudents;
    }

    public void setMaxNumberStudents(int maxNumberStudents) {
        this.maxNumberStudents = maxNumberStudents;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %s\n", courseName, groupName, maxNumberStudents, teacher);
    }
}
