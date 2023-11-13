package tempUser;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import model.User;

import java.util.List;

public class UserDAOTestLauncher {
    public static void main(String[] args) {
        // Verbinding maken met database
        DBAccess dBaccess = new DBAccess("QuizMaster","userQuizMaster","userQuizMasterPW");
        dBaccess.openConnection();
        UserDAO userDAO = new UserDAO(dBaccess);

        // Één user opslaan in de database.
        User ravelUser = new User("ravelmau","pw123", "Maurice","","Ravel","Student");
        userDAO.storeOne(ravelUser);

        // Alle users uitlezen uit de database.
        List<User> usersInTheDatabase = userDAO.getAll();
        for (User user : usersInTheDatabase) {
            System.out.println(user);
        }

        // Één user uitlezen op basis van zijn primary key.
        User user = userDAO.getOneById(1);
        System.out.println(user);

        // Verbinding met database afsluiten
        dBaccess.closeConnection();
    }
}
