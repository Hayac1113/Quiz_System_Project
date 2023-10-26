package testController;

import database.mysql.CursusDAO;
import database.mysql.DBAccess;

public class CursusDAOTestLauncher {
    public static void main(String[] args) {
        //Testcode voor CursusDAO
        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
        dBaccess.openConnection();
        CursusDAO cursusDAO = new CursusDAO(dBaccess);
        cursusDAO.insertCursusFromCSVToDB();
        dBaccess.closeConnection();
    }
}
