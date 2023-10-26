package database.mysql;

import model.User;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends AbstractDAO implements GenericDAO<User>{ // add implements GenericDAO<> !!!
    public UserDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    public void insertUsersFromCSVToDB () {
        List<User> users = User.loadCSV("Gebruikers.csv");
        for (User user : users) {
            String sql = "INSERT INTO User VALUES(?, ?, ?, ?, ?, ?);";
            try {
                setupPreparedStatement(sql);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getFirstName());
                preparedStatement.setString(4, user.getInfix());
                preparedStatement.setString(5, user.getLastName());
                preparedStatement.setString(6, user.getRole());
                executeManipulateStatement();
            } catch (SQLException sqlFout) {
                System.out.println(sqlFout);
            }
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getOneByPK(User PK) {
        return null;
    }

    @Override
    public void storeOne(User type) {

    }
}
