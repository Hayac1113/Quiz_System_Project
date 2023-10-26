package model;

public class Group {

    private String courseName;
    private String groupName;
    private int maxNumberStudent;
    private  String teacher;

    public Group(String courseName, String groupName, int maxNumberStudent, String teacher) {
        this.courseName = courseName;
        this.groupName = groupName;
        this.maxNumberStudent = maxNumberStudent;
        this.teacher = teacher;
    }

    public Group(){
        this("Onbekend", "Onbekend", 0, "Onbekend");
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMaxNumberStudent() {
        return maxNumberStudent;
    }

    public void setMaxNumberStudent(int maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %s\n", courseName, groupName, maxNumberStudent, teacher);
    }
}
