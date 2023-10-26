package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;

public class UserDAOTestLauncher {
    public static void main(String[] args) {
        DBAccess dBaccess = new DBAccess("QuizMaster","userQuizMaster","userQuizMasterPW");
        dBaccess.openConnection();
        UserDAO userDAO = new UserDAO(dBaccess);
        userDAO.insertUsersFromCSVToDB();
        dBaccess.closeConnection();
    }
}