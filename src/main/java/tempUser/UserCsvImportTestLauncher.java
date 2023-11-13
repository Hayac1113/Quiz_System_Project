package tempUser;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserCsvImportTestLauncher {
    public static void main(String[] args) {
        // Verbinding maken met database
        DBAccess dBaccess = new DBAccess("QuizMaster","userQuizMaster","userQuizMasterPW");
        dBaccess.openConnection();
        UserDAO userDAO = new UserDAO(dBaccess);

        // User importeren van CSV-file
        List<User> listOfUsers = importCsvUsers("Gebruikers.csv");

        // Users inserten in database
        for (User user : listOfUsers) {
            userDAO.storeOne(user);
        }

        // Verbinding met database afsluiten
        dBaccess.closeConnection();
    }

    // Method om Gebruikers.csv in te lezen naar Java
    public static List<User> importCsvUsers(String fileName) {
        List<User> listOfUsers = new ArrayList<>();
        File filename2 = new File(String.format("src/main/resources/CSV bestanden/%s", fileName));
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
                listOfUsers.add(new User(username, password, firstName, infix, lastName, role));
            }
            return listOfUsers;
        } catch (FileNotFoundException notFound){
            System.out.println("File not found");
            return null;
        }
    }
}
