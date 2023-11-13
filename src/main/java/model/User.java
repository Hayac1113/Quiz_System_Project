package model;

public class User {
    private int userId; //tijdelijk niet final
    private final String userName;
    private final String password;
    private final String firstName;
    private final String infix;
    private final String lastName;
    private final String role;

    public User(int userId, String userName, String password, String firstName, String infix, String lastName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String userName, String password, String firstName, String infix, String lastName, String role) {
        this(0, userName, password, firstName, infix, lastName, role);
    }
    public User() {
        this(0, "Onbekend", "Onbekend", "Onbekend", "Onbekend", "Onbekend", "Onbekend");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) { // tijdelijke setter
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getInfix() {
        return infix;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("%-10s%-25s", userName, role);
    }
}
