package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String infix;
    private String lastName;
    private String role;

    public User(String username, String password, String firstName, String infix, String lastName, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.role = role;
    }

    public User(){
        this("Onbekend","Onbekend","Onbekend","Onbekend","Onbekend",
                "Onbekend");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static List<User> loadCSV(String filename) {
        List<User> listOfUser = new ArrayList<>();
        File filename2 = new File(String.format("src/main/resources/CSV bestanden/%s", filename));
        try {
            Scanner input = new Scanner(filename2);
            while (input.hasNextLine()){
                String [] lineArray = input.nextLine().split(",");
                String username = lineArray[0];
                String password = lineArray[1];
                String firstName = lineArray[2];
                String infix= lineArray[3];
                String lastName = lineArray[4];
                String role = lineArray[5];
                listOfUser.add(new User(username, password, firstName, infix, lastName, role));
            }
        } catch (FileNotFoundException notFound){
            System.out.println("File not found");
        }
        return listOfUser;

    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s\n", username, password, firstName, infix, lastName, role);
    }
}
